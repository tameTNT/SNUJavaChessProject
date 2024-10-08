import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Stream;

//======================================================Don't modify below===============================================================//
enum PieceType {king, queen, bishop, knight, rook, pawn, none}
enum PlayerColor {black, white, none}

//Name: HUELLE LUCA
//StudentID#: 2023-81600
public class ChessBoard {
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private JPanel chessBoard;
    private JButton[][] chessBoardSquares = new JButton[8][8];
    private Piece[][] chessBoardStatus = new Piece[8][8];
    private ImageIcon[] pieceImage_b = new ImageIcon[7];
    private ImageIcon[] pieceImage_w = new ImageIcon[7];
    private JLabel message = new JLabel("Enter Reset to Start");

    ChessBoard(){
        initPieceImages();
        initBoardStatus();
        initializeGui();
    }

    public final void initBoardStatus(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++) chessBoardStatus[j][i] = new Piece();
        }
    }

    public final void initPieceImages(){
        pieceImage_b[0] = new ImageIcon(new ImageIcon("./img/king_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[1] = new ImageIcon(new ImageIcon("./img/queen_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[2] = new ImageIcon(new ImageIcon("./img/bishop_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[3] = new ImageIcon(new ImageIcon("./img/knight_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[4] = new ImageIcon(new ImageIcon("./img/rook_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[5] = new ImageIcon(new ImageIcon("./img/pawn_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_b[6] = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));

        pieceImage_w[0] = new ImageIcon(new ImageIcon("./img/king_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[1] = new ImageIcon(new ImageIcon("./img/queen_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[2] = new ImageIcon(new ImageIcon("./img/bishop_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[3] = new ImageIcon(new ImageIcon("./img/knight_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[4] = new ImageIcon(new ImageIcon("./img/rook_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[5] = new ImageIcon(new ImageIcon("./img/pawn_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
        pieceImage_w[6] = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
    }

    public ImageIcon getImageIcon(Piece piece){
        if(piece.color.equals(PlayerColor.black)){
            if(piece.type.equals(PieceType.king)) return pieceImage_b[0];
            else if(piece.type.equals(PieceType.queen)) return pieceImage_b[1];
            else if(piece.type.equals(PieceType.bishop)) return pieceImage_b[2];
            else if(piece.type.equals(PieceType.knight)) return pieceImage_b[3];
            else if(piece.type.equals(PieceType.rook)) return pieceImage_b[4];
            else if(piece.type.equals(PieceType.pawn)) return pieceImage_b[5];
            else return pieceImage_b[6];
        }
        else if(piece.color.equals(PlayerColor.white)){
            if(piece.type.equals(PieceType.king)) return pieceImage_w[0];
            else if(piece.type.equals(PieceType.queen)) return pieceImage_w[1];
            else if(piece.type.equals(PieceType.bishop)) return pieceImage_w[2];
            else if(piece.type.equals(PieceType.knight)) return pieceImage_w[3];
            else if(piece.type.equals(PieceType.rook)) return pieceImage_w[4];
            else if(piece.type.equals(PieceType.pawn)) return pieceImage_w[5];
            else return pieceImage_w[6];
        }
        else return pieceImage_w[6];
    }

    public final void initializeGui(){
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        JButton startButton = new JButton("Reset");
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                initiateBoard();
            }
        });

        tools.add(startButton);
        tools.addSeparator();
        tools.add(message);

        chessBoard = new JPanel(new GridLayout(0, 8));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(chessBoard);
        ImageIcon defaultIcon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
        Insets buttonMargin = new Insets(0,0,0,0);
        for(int i=0; i<chessBoardSquares.length; i++) {
            for (int j = 0; j < chessBoardSquares[i].length; j++) {
                JButton b = new JButton();
                b.addActionListener(new ButtonListener(i, j));
                b.setMargin(buttonMargin);
                b.setIcon(defaultIcon);
                if((j % 2 == 1 && i % 2 == 1)|| (j % 2 == 0 && i % 2 == 0)) b.setBackground(Color.WHITE);
                else b.setBackground(Color.gray);
                b.setOpaque(true);
                b.setBorderPainted(false);
                chessBoardSquares[j][i] = b;
            }
        }

        for (int i=0; i < 8; i++) {
            for (int j=0; j < 8; j++) chessBoard.add(chessBoardSquares[j][i]);

        }
    }

    public final JComponent getGui() {
        return gui;
    }

    public static void main(String[] args) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                ChessBoard cb = new ChessBoard();
                JFrame f = new JFrame("Chess");
                f.add(cb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);
                f.setResizable(false);
                f.pack();
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
    }

    //================================Utilize these functions========================================//

    class Piece{
        PlayerColor color;
        PieceType type;

        Piece(){
            color = PlayerColor.none;
            type = PieceType.none;
        }
        Piece(PlayerColor color, PieceType type){
            this.color = color;
            this.type = type;
        }
    }

    public void setIcon(int x, int y, Piece piece){
        chessBoardSquares[y][x].setIcon(getImageIcon(piece));
        chessBoardStatus[y][x] = piece;
    }

    public Piece getIcon(int x, int y){
        return chessBoardStatus[y][x];
    }

    public void markPosition(int x, int y){
        chessBoardSquares[y][x].setBackground(Color.pink);
    }

    public void unmarkPosition(int x, int y){
        if((y % 2 == 1 && x % 2 == 1)|| (y % 2 == 0 && x % 2 == 0)) chessBoardSquares[y][x].setBackground(Color.WHITE);
        else chessBoardSquares[y][x].setBackground(Color.gray);
    }

    public void setStatus(String inpt){
        message.setText(inpt);
    }

    public void initiateBoard(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++) setIcon(i, j, new Piece());
        }
        setIcon(0, 0, new Piece(PlayerColor.black, PieceType.rook));
        setIcon(0, 1, new Piece(PlayerColor.black, PieceType.knight));
        setIcon(0, 2, new Piece(PlayerColor.black, PieceType.bishop));
        setIcon(0, 3, new Piece(PlayerColor.black, PieceType.queen));
        setIcon(0, 4, new Piece(PlayerColor.black, PieceType.king));
        setIcon(0, 5, new Piece(PlayerColor.black, PieceType.bishop));
        setIcon(0, 6, new Piece(PlayerColor.black, PieceType.knight));
        setIcon(0, 7, new Piece(PlayerColor.black, PieceType.rook));
        for(int i=0;i<8;i++){
            setIcon(1, i, new Piece(PlayerColor.black, PieceType.pawn));
            setIcon(6, i, new Piece(PlayerColor.white, PieceType.pawn));
        }
        setIcon(7, 0, new Piece(PlayerColor.white, PieceType.rook));
        setIcon(7, 1, new Piece(PlayerColor.white, PieceType.knight));
        setIcon(7, 2, new Piece(PlayerColor.white, PieceType.bishop));
        setIcon(7, 3, new Piece(PlayerColor.white, PieceType.queen));
        setIcon(7, 4, new Piece(PlayerColor.white, PieceType.king));
        setIcon(7, 5, new Piece(PlayerColor.white, PieceType.bishop));
        setIcon(7, 6, new Piece(PlayerColor.white, PieceType.knight));
        setIcon(7, 7, new Piece(PlayerColor.white, PieceType.rook));
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++) unmarkPosition(i, j);
        }
        onInitiateBoard();
    }
//======================================================Don't modify above==============================================================//	




    //======================================================Implement below=================================================================//
    enum PlayerColor {  // override the previous declaration above to add opponent() helper method
        black,
        white,
        none;

        /**
         * @return the opponent of this player color (none's opponent is none)
         */
        public PlayerColor opponent() {
            return switch (this) {
                case black -> white;
                case white -> black;
                default -> none;
            };
        }
    }

    enum ActionStateType {NONE, MARK}
    enum GameStateType {NONE, CHECK, CHECKMATE, DRAW}
    // TODO: Dead position/insufficient material, threefold repetition, 50 move rule DRAW conditions
    enum DrawType {NONE, STALEMATE, DEAD_POSITION, THREEFOLD_REP, FIFTY_MOVE_RULE}

    private PlayerColor currentPlayer;
    private PlayerColor winner = PlayerColor.none;

    private ActionStateType actionState = ActionStateType.NONE;
    private GameStateType gameState = GameStateType.NONE;
    private DrawType drawState = DrawType.NONE;

    private int selX, selY;  // piece which was clicked and who's moves are being displayed

    ArrayList<Integer[]> markedMoves = new ArrayList<>();  // spaces which are currently marked (colored pink)

    class ButtonListener implements ActionListener{
        int x;
        int y;
        ButtonListener(int x, int y){
            this.x = x;
            this.y = y;
        }
        public void actionPerformed(ActionEvent e) { // Only modify here
            // Main gameplay/GUI logic block. Utilises helper functions below.
            if (gameState == GameStateType.CHECKMATE || gameState == GameStateType.DRAW) {
                updateStatus("Press Reset to play again");
                return;
            }

            Piece clickedPiece = getIcon(x, y);
            if (actionState == ActionStateType.MARK && isMarked(x, y)) {  // clicked on a valid move space => move piece
                // Actually move piece
                setIcon(x, y, getIcon(selX, selY));
                setIcon(selX, selY, new Piece());  // clear origin space

                // Check whether game is over and then check status of the move
                if (!isGameOver()) {
                    if (isThreateningKing(currentPlayer)) {
                        gameState = GameStateType.CHECK;
                    } else {
                        gameState = GameStateType.NONE;
                    }
                }

                // End player's turn.
                onEndTurn();
            } else if (clickedPiece.color != PlayerColor.none) {
                if (clickedPiece.color == currentPlayer) {  // currentPlayer's piece clicked => mark possible moves
                    // Display possible moves for clicked piece after unmarking previous
                    unmarkAllSpaces();
                    selX = x;
                    selY = y;

                    markedMoves = pieceMovesMap.get(clickedPiece.type).apply(selX, selY);
                    markedMoves = legaliseMoveSet(selX, selY, markedMoves);  // only display moves which don't put/leave the king in check
                    
                    if (markedMoves.isEmpty()) {
                        updateStatus("No legal moves for selected piece");
                    } else {
                        actionState = ActionStateType.MARK;
                        for (Integer[] space : markedMoves) {
                            markPosition(space[0], space[1]);
                        }
                        updateStatus("Click marked space to move; unmarked to cancel");
                    }
                } else {
                    unmarkAllSpaces();
                    updateStatus("Wrong player's piece");
                }
            } else {  // clicked on an empty space so potentially cancelling a move => unmark all spaces
                unmarkAllSpaces();
                updateStatus("Click a piece to make a move");
            }
        }
    }

    //=== Piece movement functions ===

    //== Helper functions ==

    /**
     * @param x y-coordinate of chess board (top left origin)
     * @param y x-coordinate of chess board (top left origin)
     * @return true if space is NOT in bounds of the board; false if in bounds
     */
    boolean isNotInBounds(int x, int y) {
        return x < 0 || x > 7 || y < 0 || y > 7;
    }

    /**
     * @param x y-coordinate of chess board (top left origin)
     * @param y x-coordinate of chess board (top left origin)
     * @return true if the space is marked (pink), false if out of bounds or not marked
     */
    boolean isMarked(int x, int y) {
        if (isNotInBounds(x, y)) return false; // out of bounds
        return chessBoardSquares[y][x].getBackground() == Color.pink;
    }

    /**
     * @param x y-coordinate of chess board (top left origin)
     * @param y x-coordinate of chess board (top left origin)
     * @return true if space is empty AND not out of bounds
     */
    boolean isEmpty(int x, int y) {
        if (isNotInBounds(x, y)) return false; // out of bounds
        return getIcon(x, y).color == PlayerColor.none;
    }

    /**
     * @param for_ player perspective to consider (is the enemy from their perspective?)
     * @param x y-coordinate of chess board (top left origin)
     * @param y x-coordinate of chess board (top left origin)
     * @return true if space is occupied by an enemy piece from for_'s perspective
     *   and false if out of bounds, empty, or friendly.
     */
    boolean isEnemyFor(PlayerColor for_, int x, int y) {
        if (isNotInBounds(x, y)) return false; // out of bounds
        PlayerColor spaceColor = getIcon(x, y).color;
        return spaceColor != for_ && spaceColor != PlayerColor.none;
    }

    /**
     * Generates moves in a given direction until a friendly/enemy (capture-able so a valid move) piece is encountered
     * or the edge of the board is reached, adding to these to the ArrayList moves.
     *
     * @param startX y-coordinate of starting position (top left origin)
     * @param startY x-coordinate of starting position (top left origin)
     * @param direction Integer[] of length 2, representing the direction to apply movement in (e.g. [1, 0] is down 1 space)
     * @param moves ArrayList of possible Integer[] moves to add further moves to
     * @param continuous whether the piece can move continuously in this direction
     *   (e.g., a rook can move any number of spaces horizontally or vertically but a king only one space)
     */
    void advanceInDirection(int startX, int startY, Integer[] direction, ArrayList<Integer[]> moves, boolean continuous) {
        int x = startX + direction[0];
        int y = startY + direction[1];
        int iterations = 0;
        while (isEmpty(x, y) && (continuous || iterations == 0)) {
            moves.add(new Integer[]{x, y});
            x += direction[0];
            y += direction[1];
            iterations++;
        }
        // can also capture an enemy piece at end of path
        PlayerColor pieceColor = getIcon(startX, startY).color;
        if (isEnemyFor(pieceColor, x, y) && (continuous || iterations == 0)) {
            moves.add(new Integer[]{x, y});
        }
    }

    //== Piece movement generation ==

    // Mapping of piece move types to a function which generates possible moves for that piece
    // Usage: pieceMovesMap.get(PieceType).apply(x, y) -> ArrayList<Integer[]> of possible moves
    Map<PieceType, BiFunction<Integer, Integer, ArrayList<Integer[]>>> pieceMovesMap = Map.of(
            PieceType.pawn, this::pawnMoves,
            PieceType.bishop, this::bishopMoves,
            PieceType.knight, this::knightMoves,
            PieceType.rook, this::rookMoves,
            PieceType.queen, this::queenMoves,
            PieceType.king, this::kingMoves
    );

    // Unit directions for repeated use in piece movement functions below
    Integer[][] diagonalUnits = {{1, 1}, {-1, 1}, {1, -1}, {-1, -1}};
    Integer[][] horizontalUnits = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
    Integer[][] bothUnits = Stream.concat(Arrays.stream(diagonalUnits), Arrays.stream(horizontalUnits))
            .toArray(Integer[][]::new);

    // TODO: special move additions - en passant, castling, pawn promotion

    /**
     * @param currentX y-coordinate of pawn to move (top left origin)
     * @param currentY x-coordinate of pawn to move (top left origin)
     * @return ArrayList of possible moves for pawn at (currentX, currentY) - these are NOT necessarily legal moves
     */
    ArrayList<Integer[]> pawnMoves(int currentX, int currentY) {
        ArrayList<Integer[]> moves = new ArrayList<>();
        PlayerColor pawnColor = getIcon(currentX, currentY).color;
        int xDirection = (pawnColor == PlayerColor.black) ? 1 : -1;

        // Check if at edge of board and as such, unable to move (would be promoted in fully functional chess game)
        if (currentX + xDirection < 0 || currentX + xDirection > 7) return moves;

        // Check if space directly in front is empty
        if (isEmpty(currentX + xDirection, currentY)) {
            moves.add(new Integer[]{currentX + xDirection, currentY});

            // If pawn is on starting row, can move 2 spaces forward as a special case
            if ((currentX - xDirection) % 7 == 0) {
                int firstMoveX = currentX + 2 * xDirection;
                if (isEmpty(firstMoveX, currentY)) {
                    moves.add(new Integer[]{firstMoveX, currentY});
                }
            }
        }

        // Diagonal attacking (diagonal) rules apply if there is an opponent piece present
        for (Integer d : new Integer[]{-1, 1}) {
            int diagonalX = currentX + xDirection;
            int diagonalY = currentY + d;
            if (isEnemyFor(pawnColor, diagonalX, diagonalY)) {
                moves.add(new Integer[]{currentX + xDirection, currentY + d});
            }
        }

        return moves;
    }

    /**
     * @param currentX y-coordinate of bishop to move (top left origin)
     * @param currentY x-coordinate of bishop to move (top left origin)
     * @return ArrayList of possible moves for bishop at (currentX, currentY) - these are NOT necessarily legal moves
     */
    ArrayList<Integer[]> bishopMoves(int currentX, int currentY) {
        ArrayList<Integer[]> moves = new ArrayList<>();

        // Generate moves in all 4 diagonal directions
        for (Integer[] direction : diagonalUnits) {
            advanceInDirection(currentX, currentY, direction, moves, true);
        }

        return moves;
    }

    /**
     * @param currentX y-coordinate of knight to move (top left origin)
     * @param currentY x-coordinate of knight to move (top left origin)
     * @return ArrayList of possible moves for knight at (currentX, currentY) - these are NOT necessarily legal moves
     */
    ArrayList<Integer[]> knightMoves(int currentX, int currentY) {
        ArrayList<Integer[]> moves = new ArrayList<>();

        // Generate moves in all 8 horsey funny directions
        for (Integer[] direction : new Integer[][]{{1, 2}, {-1, 2}, {1, -2}, {-1, -2},
                                                   {2, 1}, {-2, 1}, {2, -1}, {-2, -1}}) {
            advanceInDirection(currentX, currentY, direction, moves, false);
        }

        return moves;
    }

    /**
     * @param currentX y-coordinate of rook to move (top left origin)
     * @param currentY x-coordinate of rook to move (top left origin)
     * @return ArrayList of possible moves for rook at (currentX, currentY) - these are NOT necessarily legal moves
     */
    ArrayList<Integer[]> rookMoves(int currentX, int currentY) {
        ArrayList<Integer[]> moves = new ArrayList<>();

        // Generate moves in all 4 compass directions
        for (Integer[] direction : horizontalUnits) {
            advanceInDirection(currentX, currentY, direction, moves, true);
        }

        return moves;
    }

    /**
     * @param currentX y-coordinate of queen to move (top left origin)
     * @param currentY x-coordinate of queen to move (top left origin)
     * @return ArrayList of possible moves for queen at (currentX, currentY) - these are NOT necessarily legal moves
     */
    ArrayList<Integer[]> queenMoves(int currentX, int currentY) {
        ArrayList<Integer[]> moves = new ArrayList<>();

        // Generate moves in all 4 horizontal and all 4 diagonal directions
        for (Integer[] direction : bothUnits) {
            advanceInDirection(currentX, currentY, direction, moves, true);
        }

        return moves;
    }

    /**
     * @param currentX y-coordinate of king to move (top left origin)
     * @param currentY x-coordinate of king to move (top left origin)
     * @return ArrayList of possible moves for king at (currentX, currentY) - these are NOT necessarily legal moves
     */
    ArrayList<Integer[]> kingMoves(int currentX, int currentY) {
        ArrayList<Integer[]> moves = new ArrayList<>();

        // Generate single move in all 4 horizontal and all 4 diagonal directions
        for (Integer[] direction : bothUnits) {
            advanceInDirection(currentX, currentY, direction, moves, false);
        }

        return moves;
    }

    /**
     * Creates a new legalised ArrayList of moves, given a set of provided possible moves,
     * where the player doesn't move themselves into check.
     *
     * @param pieceX y-coordinate of piece to move via proposedMoves (top left origin)
     * @param pieceY x-coordinate of piece to move via proposedMoves (top left origin)
     * @param proposedMoves ArrayList of potential moves to be inspected for legality. This ArrayList is not modified.
     * @return ArrayList of legal moves which the player can make. These can and should be displayed/marked.
     */
    ArrayList<Integer[]> legaliseMoveSet(Integer pieceX, Integer pieceY, ArrayList<Integer[]> proposedMoves) {
        ArrayList<Integer[]> legalMoves = new ArrayList<>();
        for (Integer[] move : proposedMoves) {
            PlayerColor movingPlayer = getIcon(pieceX, pieceY).color;

            // temporarily perform move
            Integer moveX = move[0];
            Integer moveY = move[1];
            Piece movingPiece = getIcon(pieceX, pieceY);
            Piece targetSpace = getIcon(moveX, moveY);
            chessBoardStatus[pieceY][pieceX] = new Piece();
            chessBoardStatus[moveY][moveX] = movingPiece;

            // Test whether this temporary move would result in a direct attack on movingPlayer's king from their opponent
            // i.e. you can't move into a check on yourself
            PlayerColor opposingPlayer = movingPlayer.opponent();
            if (!isThreateningKing(opposingPlayer)) {
                legalMoves.add(move);  // valid move that doesn't put movingPlayer's own king in check so add to legalMoves
            }

            // undo temporary move
            chessBoardStatus[pieceY][pieceX] = movingPiece;
            chessBoardStatus[moveY][moveX] = targetSpace;
        }
        return legalMoves;
    }

    //=== Game Logic functions ===

    /**
     * Check for discovered check/potential attack on king (e.g., a move revealed an attack on the king)
     *
     * @param attackingColor color of the player who could potentially be attacking the enemy king (opposite color)
     * @return true if king is under attack from attackingColor, false otherwise
     */
    boolean isThreateningKing(PlayerColor attackingColor) {
        // iterate over every piece of attackingColor and see if it can attack the enemy king
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8 ; y++) {
                Piece piece = getIcon(x, y);
                if (piece.color == attackingColor) {
                    ArrayList<Integer[]> attackMap = pieceMovesMap.get(piece.type).apply(x, y);
                    for (Integer[] space : attackMap) {
                        if (getIcon(space[0], space[1]).type == PieceType.king) {
                            return true;  // there is a move which targets the king so the king is in check
                        }
                    }
                }
            }
        }
        return false;  // no moves which target the king were found so no check
    }

    /**
     * Checks whether the game is over (checkmate or stalemate) after currentPlayer's most recent move.
     * Updates the gameState and winner variables accordingly.
     *
     * @return true if game is over, false if the opponent to currentPlayer has a valid move on their next turn
     */
    boolean isGameOver() {
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8 ; y++) {
                Piece piece = getIcon(x, y);
                if (piece.color == currentPlayer.opponent()) {  // check if opponent has any valid moves
                    ArrayList<Integer[]> pieceMoves = pieceMovesMap.get(piece.type).apply(x, y);
                    pieceMoves = legaliseMoveSet(x, y, pieceMoves);  // only consider moves which don't put/leave the king in check
                    if (!pieceMoves.isEmpty()) {
                        return false;  // valid move found - the opposing player is not checkmated
                    }
                }
            }
        }

        // Check for stalemate (no valid moves but king not in check)
        if (isThreateningKing(currentPlayer)) {  // Checkmate (no valid moves (from above) AND king threatened)
            winner = currentPlayer;  // currentPlayer wins if opponent has no legal moves on their next turn
            gameState = GameStateType.CHECKMATE;
        } else {  // stalemate
            // Ref: https://www.chess.com/terms/draw-chess#stalemate
            gameState = GameStateType.DRAW;
            drawState = DrawType.STALEMATE;
        }
        return true;
    }

    //=== Interface Logic functions ===

    /**
     * Call when the reset button is pressed after pieces are reset.
     * Resets game tracking variables and displays initial status message.
     */
    void onInitiateBoard(){
        currentPlayer = PlayerColor.black;  // black starts the game
        winner = PlayerColor.none;

        actionState = ActionStateType.NONE;
        gameState = GameStateType.NONE;
        drawState = DrawType.NONE;

        unmarkAllSpaces();
        updateStatus("The game has begun!");
    }

    /**
     * Performs clean up after a player's turn has ended.
     * Unmarks spaces, swaps currentPlayer variable, and updates status message.
     */
    void onEndTurn() {
        unmarkAllSpaces();
        currentPlayer = currentPlayer.opponent();
        updateStatus("");
    }

    /**
     * Unmarks all spaces, clears the markedMoves ArrayList and resets actionState to NONE.
     */
    void unmarkAllSpaces() {
        for (Integer[] move : markedMoves) {
            unmarkPosition(move[0], move[1]);
        }
        markedMoves.clear();
        actionState = ActionStateType.NONE;
    }

    /**
     * Updates the status message with the current game state and action state and any additional relevant messages.
     *
     * @param preMsg Custom message to display at the very start of the status text.
     */
    void updateStatus(String preMsg) {
        String statusString = "";
        if (!preMsg.isEmpty()) statusString += preMsg + " / ";

        if (gameState == GameStateType.CHECKMATE) {
            statusString += "Game Over! / CHECKMATE - " + winner.toString().toUpperCase() + " wins!";
        } else if (gameState == GameStateType.DRAW) {
            statusString += "Game Over!";
            if (drawState == DrawType.STALEMATE) {
                statusString += " (by stalemate)";
            }
            statusString += " / DRAW (no winner)";
        } else {
            statusString += String.format("%s's turn", currentPlayer.toString().toUpperCase());
        }

        if (gameState == GameStateType.CHECK) {
            statusString += " / CHECK!";
        }

        if (actionState == ActionStateType.MARK) {
            statusString += " / Marking moves...";
        }

        setStatus(statusString);
    }
}

