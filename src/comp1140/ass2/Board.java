package comp1140.ass2;

/**
 * Created by ***REMOVED*** on 19/08/15.
 * @author ***REMOVED*** ***REMOVED***, ***REMOVED***
 */
public class Board {

    private CellState[][] grid;
    private Piece[] unplacedPiecesRed = new Piece['U'-'A'];


    private boolean[] lastMove = new boolean[]{false, false, false, false};

    public boolean[] getLastMove() {
        return lastMove;
    }

    private Piece[] unplacedPiecesGreen;
    private Piece[] unplacedPiecesBlue=new Piece['U'-'A'];
    private Piece[] unplacedPiecesYellow;
    private Piece[][] unplacedPieces =
            {unplacedPiecesRed
            ,unplacedPiecesGreen
            ,unplacedPiecesBlue
            ,unplacedPiecesYellow};

    private int currentTurn;

    /**
     *
     * @return CellState[][] this.grid
     */
    public CellState[][] getGrid() {
        return grid;
    }

    /**
     *
     * Given a four character String and the identifying number of the current player,
     * sets the grid coordinates to that player's colour where the piece is played.
     *
     * @param move   a four character string representing a single move
     * @return void  while also changing this.grid
     */
    public void placePiece(String move) {

        /* Split up the String move */
        char pieceChar = move.charAt(0);
        char rotation  = move.charAt(1);
        char x         = move.charAt(2);
        char y         = move.charAt(3);

        int playerId = currentTurn % 4;

        Coordinate coordinate = new Coordinate(x-'A',y-'A');
        Piece piece = unplacedPieces[playerId][pieceChar-'A'];

        /* Remove piece from unplacedPieces */
        unplacedPieces[playerId][pieceChar-'A'] = null;

        /* @TODO once Piece.getOccupiedCells is finished, uncomment line */

        piece.shape.initialisePiece(coordinate,rotation);                       // @TODO check Tim's edit is consistent
        Coordinate[] cells = piece.shape.getOccupiedCells();                    //Tim's edit: Inserted 'getOccupiedCells'
        //Coordinate[] cells = {new Coordinate(playerId,playerId)};


        /* @TODO find better way to get CellState */
        CellState[] cellstates = {CellState.Blue, CellState.Yellow, CellState.Red, CellState.Green};
        CellState turnColour = cellstates[playerId];

        for(Coordinate cell : cells) {
            if(cell!=null) grid[cell.getY()][cell.getX()] = turnColour;
        }
    }

    /**
     * Board's toString function, currently used for debugging.
     * @return String a string representation of the board, made up of the intials of each colour.
     */
    public String toString() {
        String string = "";
        /* @TODO replace with foreach / for(:)? */
        for(int i=0;i<grid.length;i++) {
            for(int j = 0;j<grid[i].length;j++) {
                /* We use substring instead of charAt to able to subsequently use replace */
                string += grid[i][j].name().substring(0,1).replace("E","•")+" ";
            }
            string += "\n";
        }
        return string;
    }

    /**
     * Initialises a Board object from a string.
     * @param game A string representing the set of moves so far
     */
    public Board(String game) {
        grid = new CellState['T'-'A']['T'-'A'];
        for(int i=0;i<grid.length;i++)
            for(int j=0;j<grid[0].length;j++)
                grid[i][j]=CellState.Empty;
        int players = 4;

        /**
         * Fill up array of unused pieces
         */
        for(int colourIndex = 0; colourIndex<players; colourIndex++) {
            Colour colour = Colour.values()[colourIndex];
            unplacedPieces[colourIndex] = new Piece[]{
                    new Piece(Shape.A, colour),                     //@ToDo check Tim's edit is consistent
                    new Piece(Shape.B, colour),
                    new Piece(Shape.C, colour),
                    new Piece(Shape.D, colour),                     //@ToDo check Tim's edit is consistent
                    new Piece(Shape.E, colour),                     //@ToDo check Tim's edit is consistent
                    new Piece(Shape.F, colour),                     //@ToDo check Tim's edit is consistent
                    new Piece(Shape.G, colour),                     //@ToDo check Tim's edit is consistent
                    new Piece(Shape.H, colour),                     //@ToDo check Tim's edit is consistent
                    new Piece(Shape.I, colour),                     //@ToDo check Tim's edit is consistent
                    new Piece(Shape.J, colour),                     //@ToDo check Tim's edit is consistent
                    new Piece(Shape.K, colour),                     //@ToDo check Tim's edit is consistent
                    new Piece(Shape.L, colour),                     //@ToDo check Tim's edit is consistent
                    new Piece(Shape.M, colour),                     //@ToDo check Tim's edit is consistent
                    new Piece(Shape.N, colour),                     //@ToDo check Tim's edit is consistent
                    new Piece(Shape.O, colour),                     //@ToDo check Tim's edit is consistent
                    new Piece(Shape.P, colour),                     //@ToDo check Tim's edit is consistent
                    new Piece(Shape.Q, colour),                     //@ToDo check Tim's edit is consistent
                    new Piece(Shape.R, colour),                     //@ToDo check Tim's edit is consistent
                    new Piece(Shape.S, colour),                     //@ToDo check Tim's edit is consistent
                    new Piece(Shape.T, colour),                     //@ToDo check Tim's edit is consistent
                    new Piece(Shape.U, colour)
            };
        }

        /* Remove any spaces */
        game = game.replace(" ","");
        /** Parse string game and turn it into a board */
        while(game.length()>0) {

            /** Check for a pass, represented by the character '.'. */
            if(game.charAt(0)=='.') {
                game = game.substring(1);
                currentTurn++;
                continue;
            }
            /** Get the next four chars and parse the individual move. */
            String turn = game.substring(0,4);
            placePiece(turn);
            game = game.substring(4);
            currentTurn++;
        }
    }

    public static String[] splitMoves (String game) {
        game.replace(" ","");
        int passCount = game.length() - game.replace(".","").length();
        int moveCount = (game.length() - passCount) / 4;
        int totalCount = passCount + moveCount;
        if( (passCount+4*moveCount) != game.length())
            throw new IllegalArgumentException("Invalid game");
        String[] moves = new String[totalCount];

        int index=0;
        for(int i=0;i<totalCount;i++){
            if(game.charAt(index)=='.') {
                moves[i]=".";
                index++;
                continue;
            }
            moves[i]=game.substring(index,index+4);
            index+=4;
        }

        return moves;
    }

    public boolean legitimateMove(String move) {
        return true;
    }
}
