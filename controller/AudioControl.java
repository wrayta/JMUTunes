//package controller;
//
//import java.io.*;
//
//import controller.edit.*;
//import controller.file.*;
//import controller.search.*;
//
//import model.*;
//import view.text.*;
//
///**
// * Controls the action of the JMUTunes application. This is the only class
// * that has references to the model and view classes
// * 
// * Modifications: 
// * **TAW & JMC - PA2 (2/10/2013) 
// *   - modified delete method to allow deletion of user selected file
// *   - enabled edit method to edit user selected file
// *   - created overloaded getAudioInfo(AudioFile) to handle edit
// * **TAW & JMC - PA3 (3/9/2013)
// * - added "current" to point to standard list or search list (used for delete, 
// *   edit & search).
// * - recast addAudioFile to load from disk
// * - moved type specific code for edit to new classes using Strategy pattern
// *   getAudioInfo( AudioFile) offloaded to AudioEdit & children
// * - implemented search function - offloaded to AudioSearchFile to simplify
// *   this class & bring together all search-related code into a single class
// *   
// * @author Thomas Wray & Joe Cumins
// * @version PA3 (3/9/2013), PA2 (2/12/2013), PA1 (1/19/2013)
// */
//public class AudioControl
//{
//    //----------------------------------------------------------------------
//    // Declarations
//    //----------------------------------------------------------------------
//    private int page;
//
//    private AudioList current;  // the current list (actual or search)
//    private AudioList list;     // the actual list
//    
//    private AudioView view;
//    
//    private view.gui.AudioWindow window;
//
//    // public static finals
//    public static char ADD = 'a';
//    public static char BACK = 'b';
//    public static char DELETE = 'd';
//    public static char EDIT = 'e';
//    public static char LIST = 'l';
//    public static char NEXT = 'n';
//    public static char PLAY = 'p';
//    public static char SEARCH = 's';
//    public static char QUIT = 'q';
//
//    /**
//     * Default constructor
//     * 
//     * **TAW & JMC (PA3) - Modified to set current equal to list
//     */
//    public AudioControl()
//    {
//    	model.AudioFile file = new model.MP3File("The Strokes", "Reptilia", "AAA", 3, "\\Users\\Thomas Wray\\Music\\Reptilia.mp3", "Sucker", 234);
//        page = 0;
//
//        list = new AudioList();
//        list.add(file);
//        
//        view = new AudioView();
//        
//        current = list;
//
//        window = new view.gui.AudioWindow(list);
//        
//    } // default constructor

//
//    /************************** public methods ****************************/
//
//    /**
//     * This method has the outermost level of program control. The program
//     * will display the screen, ask the user to choose an action, then
//     * respond to the action designated by the user. The program will
//     * continue until the user chooses to quit.
//     * 
//     * **TAW & JMC (PA2) - Modified for PA2 to read/write files
//     */
//    public void run() 
//    {
//        char choice = (char) 0; // default to nonsensical value
//        AudioFileControl fileController = new AudioFileControl( list, view );
//        
//        fileController.readFile();
//        
//        do
//        {
//            showScreen();
//            choice = getChoice();
//            respondToChoice( choice );
//            
//        } while ( choice != QUIT );
//        
//        fileController.writeFile();
//
//    } // method run
//
//    /************************* private methods ****************************/
//
//    /**
//     * Add an audio file to the list
//     * 
//     * **TAW & JMC (PA3) - modified to load audio files from the disk
//     */
//    private void addAudioFile()
//    {
//        int choice = 0;
//        current = list; 
//        
//        AudioFileControl fileControl = new AudioFileControl( list, view );
//        
//        String filter = "";
//        String folder = "";
//        
//        // clear the screen & print the header info
//        view.clearScreen();
//        view.centerText( "Add Audio Files" );
//        view.displayLine();
//        
//        // ask to load files
//        if ( isConfirmed( "Add audio files from disk (Y/N)? -> " ) )
//        {
//            view.displayLine();
//            view.displayLine( "Add MP3 files, Wav files, or both:" );
//            view.displayLine( "1. MP3 files" );
//            view.displayLine( "2. Wav files " );
//            view.displayLine( "3. Both" );
//            view.display( "-> " );
//            
//            choice = view.getNumber( true, 1, 3 );
//            
//            switch( choice )
//            {
//                case 1: filter = "mp3"; break;
//                case 2: filter = "wav"; break;
//                case 3: filter = "mp3wav"; break;
//                case 4: filter = "oops";
//            
//            } // end switch
//            
//            view.displayLine();
//            view.display( "Starting Folder -> " );
//            folder = view.getInput( false , "" );
//            
//            fileControl.loadFiles( new File( folder ) , filter );
//                       
//        } // end if
//                
//    } // method addAudioFile
//
//
//    /**
//     * Delete an audio file (PA1 - delete last audio file)
//     * 
//     * **TAW & JMC (PA2) - allow deletion of the file chosen by the user
//     * **TAW & JMC (PA3) - use "current" list to allow deletion from search results
//     */
//    private void deleteAudioFile()
//    {
//        if ( current.size() > 0 )
//        {
//            int fileNumber = 0;
//            AudioFile deleteFile;
//
//            view.display( "Delete file number -> " );
//            fileNumber = view.getNumber( true, 1 , list.size() );
//
//            deleteFile = current.get( fileNumber - 1 );
//
//            view.clearScreen();
//            view.centerText( "Delete Audio File" );
//            view.displayLine();
//            view.displayLine();
//
//            if ( isConfirmed( "Delete \"" + deleteFile.toString()
//                    + "\"? (Y/N) -> " ) )
//               current.remove( deleteFile );
//            
//            // delete from the full list if we are in search mode
//            if ( current != list )
//                list.remove( deleteFile );
//
//            // move to the prior page if removing the last item on a page > 1
//            if ( current.size() > 0 && current.size() < page * 16 + 1 )
//                page--;
//
//        } // end if
//
//        else
//            view.pause( "Nothing to delete." );
//
//    } // method deleteAudioFile
//
//
//    /**
//     * Edit an audio file (not functional in PA1)
//     * 
//     * **TAW & JMC (PA2) - Edit file chosen by the user
//     * **TAW & JMC (PA3) - use "current" list to allow edit of search
//     */
//    private void editAudioFile()
//    {
//        if ( current.size() > 0 )
//        {
//            int fileNumber = 0;
//            AudioFile original;
//            AudioFile edited;
//
//            view.display( "Edit file number -> " );
//            fileNumber = view.getNumber( true, 1 , current.size() );
//
//            original = current.get( fileNumber - 1 );
//
//            if ( isConfirmed( "Edit \""
//                    + original.toString()
//                    + "\"? (Y/N) -> " ) )
//            {
//                edited = getAudioInfo( original );
//
//                if ( isConfirmed( "Confim Edit (Y/N): " ) )
//                {
//                    current.remove( original );
//                    current.add( edited );
//                    
//                    // if editing from a search list, then handle remove, add
//                    // from the full list also
//                    if ( current != list ) 
//                    {
//                        list.remove( original );
//                        list.add( edited );
//                        
//                    } // end if
//
//                } // end if
//
//            } // end if
//            
//        } // end if
//       
//        else
//            view.pause( "Nothing to edit." );
//
//    } // method editAudioFile
//
//
//    /**
//     * Get the Audio file information from the user and create an AudioFile
//     * object to add to the list
//     * 
//     * **TAW & JMC (PA3) - no longer needed since add function has changed
//     * 
//     * @return an AudioFile object
//     */
//    // private AudioFile getAudioInfo()
//    
//    /**
//     * Get the Audio file information from an existing AudioFile for
//     * the user to edit
//     * 
//     * **TAW & JMC (PA2) - added for the edit function
//     * **TAW & JMC (PA3) - off-loaded to AudioEdit & children
//     * 
//     * @return an AudioFile object
//     */
//    private AudioFile getAudioInfo( AudioFile original )
//    {
//        AudioEdit editor;
//        
//        if ( original instanceof MP3File )
//            editor = new MP3FileEdit( view );
//        else
//            editor = new WavFileEdit( view );       
//        
//        return editor.editFile( original );
//
//    } // method getAudioInfo (overloaded for edit)
//
//
//    /**
//     * Get a valid menu choice from the user
//     * 
//     * @return the validated choice
//     */
//    private char getChoice()
//    {
//        boolean isValid = false;
//
//        String input;
//
//        do
//        {
//            input = view.getInput( false , "" );
//
//            isValid = isValidChoice( input , "aedslpnbq" );
//
//            if ( !isValid )
//                view.displayError( "Incorrect entry (" + input + ")" );
//            
//
//        } while ( !isValid );
//
//        return input.toLowerCase().charAt( 0 );
//
//    } // method getChoice
//
//
//    
//    /**
//     * Handle playlists (not functional in PA1)
//     */
//    private void handlePlayLists()
//    {
//        view.showUnavailable( "Maintain Playlists" );
//
//    } // method handlePlayLists
//
//
//    /**
//     * Confirms (Y/N) an operation
//     * 
//     * @return true if the user enters 'Y' or 'y'
//     */
//    private boolean isConfirmed( String message )
//    {
//        boolean confirmed = false;
//        boolean isValid = false;
//
//        String input = "";
//
//        view.displayLine();
//        
//        // make sure there is a message to print before continuing
//        if ( message != null && message.length() > 0 )
//        {
//            view.display( message );
//
//            do
//            {
//                input = view.getInput( true , "Required entry." );
//
//                isValid = isValidChoice( input , "yn" );
//
//                if ( !isValid )
//                    view.displayError( "Must be 'Y' or 'N'." );
//
//            } while ( !isValid );
//
//            confirmed = input.toLowerCase().charAt( 0 ) == 'y';
//        }
//        
//        return confirmed;
//
//    } // method isConfirmed
//
//
//    /**
//     * Determines whether the input value is among the valid choices
//     * 
//     * @param input
//     * @return true if the input value is among the valid choices
//     */
//    private boolean isValidChoice(String input, String validChoices)
//    {
//        boolean isValid = false;
//
//        // make sure there are valid choices to check & that the input is
//        // a single character 
//        if ( validChoices != null && validChoices.length() > 0 )
//            if ( input.length() == 1 )
//                if ( validChoices.indexOf( 
//                        input.toLowerCase().charAt( 0 ) ) > -1 )
//                    isValid = true;
//
//        return isValid;
//
//    } // method isValidChoice
//
//
//    /**
//     * Print the next page of audio files if there is one
//     */
//    private void nextPage()
//    {
//        if ( current.size() > page * 16 + 16 )
//            page++;
//
//    } // method nextPage
//
//
//    /**
//     * Play an audio file (not functional in PA1)
//     */
//    private void playAudioFile()
//    {
//        view.showUnavailable( "Play Audio File" );
//
//    } // method playAudioFile
//
//
//    /**
//     * Print the prior page of audio files if there is one
//     */
//    private void priorPage()
//    {
//        if ( page > 0 )
//            page--;
//        
//    } // method nextPage
//
//
//    /**
//     * Perform the operation selected by the user from the menu
//     * 
//     * @param choice
//     */
//    private void respondToChoice( char choice )
//    {
//        switch (choice)
//        {
//            case 'a':
//                addAudioFile();
//                break;
//            case 'e':
//                editAudioFile();
//                break;
//            case 'd':
//                deleteAudioFile();
//                break;
//            case 's':
//                searchAudioFiles();
//                break;
//            case 'l':
//                handlePlayLists();
//                break;
//            case 'p':
//                playAudioFile();
//                break;
//            case 'b':
//                priorPage();
//                break;
//            case 'n':
//                nextPage();
//                break;
//            case 'q':
//                view.clearScreen();
//                break;
//            default:
//                view.pause( "Oops! Error!!" );
//                showScreen();
//
//        } // end switch
//
//    } // method respondToChoice
//
//
//    /**
//     * Search for audio files - This was not functional in PA1 or PA2.
//     * 
//     * **TAW & JMC (PA3) - enabled for PA3 - created separate AudioFileSearch
//     *               class for this due to its complexity & the desire
//     *               to keep this related functionality together.
//     */
//    private void searchAudioFiles()
//    {
//        AudioFileSearch audioSearch = new AudioFileSearch( list, view );
//        AudioList resultSet = audioSearch.search();
//        
//        if ( resultSet != null )
//        {
//            page = 0; // set page for search set
//            current = resultSet;            
//            
//        } // end if        
//        else
//        {
//            current = list;
//            page = 0; // return to first page
//        
//        } // end else
//        
//    } // method searchAudioFiles
//
//
//    /**
//     * Show the body of the page
//     * 
//     * **TAW & JMC (PA3) - modified to show the AudioFiles from the current list 
//     *               (the real list or the search list)
//     */
//    private void showBody()
//    {
//        for (int i = page * 16 + 1; i < page * 16 + 17; i++)
//        {
//            view.displayLine( spaceFillNumber( i ) + ". "
//                    + current.getShortTitle( i - 1 ) );
//
//        } // end for
//
//    } // method showBody
//
//
//    /**
//     * Show the bottom of the primary screen
//     */
//    private void showFoot()
//    {
//        view.displayLine();
//        view.displayLine( "(A)dd. (E)dit, (D)elete, (S)earch, play(L)ist, "
//                + "(P)lay, (N)ext, (B)ack, (Q)uit" );
//        view.displayLine();
//        view.display( "Choose Operation -> " );
//
//    } // method showFoot
//
//
//    /**
//     * Show the top of the primary screen
//     * 
//     * **TAW & JMC (PA3) - modified to print 3rd line (for search)
//     */
//    private void showHead()
//    {
//        String lastLine = current != list ? "Search Results" : "";
//        
//        view.centerText( "JMUTunes Audio Player" );
//        view.centerText( "CS239 (Spring 2013)" );
//        view.centerText( lastLine );
//        view.displayLine();
//
//    } // method showHead
//
//
//    /**
//     * Show the primary screen
//     */
//    private void showScreen()
//    {
//        view.clearScreen();
//        showHead();
//        showBody();
//        showFoot();
//
//    } // method showScreen
//
//
//    /**
//     * Space-fill the number if necessary
//     * 
//     * @param the number to space fill
//     * @return the number as a space-filled String
//     */
//    private String spaceFillNumber( int num )
//    {
//        String numAsString = "" + num;
//        String lastNumAsString = "" + ( ( page * 16 ) + 16 );
//
//        if ( lastNumAsString.length() > numAsString.length() )
//            numAsString = " " + numAsString;
//
//        return numAsString;
//
//    } // method spaceFillNumber

// } // class AudioControl