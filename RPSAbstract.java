import java.util.Random;

public abstract class RPSAbstract implements RPSInterface {
    protected static final int SEED = 12;
    protected final Random rand = new Random(SEED);

    // The moves allowed in this version of RPS
    protected String[] possibleMoves;

    // The number of games, player wins, CPU wins, and ties
    protected int numGames;
    protected int numPlayerWins;
    protected int numCPUWins;
    protected int numTies;

    // The complete history of the moves
    protected String[] playerMoves;
    protected String[] cpuMoves;

    // The default moves. Use for the basic implementation of the game.
    protected static final String[] DEFAULT_MOVES = {"rock", "paper", "scissors"};

    /* Important: Use the following constants to avoid output matching issues and magic numbers! */

    // Messages for the game.
    protected static final String INVALID_INPUT =
            "That is not a valid move. Please try again.";
    protected static final String PLAYER_WIN = "You win.";
    protected static final String CPU_WIN = "I win.";
    protected static final String TIE = "It's a tie.";
    protected static final String CPU_MOVE = "I chose %s. ";
    protected static final String THANKS =
            "Thanks for playing!\nOur most recent games were:";
    protected static final String PROMPT_MOVE =
            "Let's play! What's your move? (Type the move or q to quit)";

    protected static final String OVERALL_STATS =
            "Our overall stats are:\n" +
                    "I won: %.2f%%\nYou won: %.2f%%\nWe tied: %.2f%%\n";
    protected static final String CPU_PLAYER_MOVES = "Me: %s, You: %s\n";

    // Game outcome constants.
    protected static final int CPU_WIN_OUTCOME = 2;
    protected static final int PLAYER_WIN_OUTCOME = 1;
    protected static final int TIE_OUTCOME = 0;
    protected static final int INVALID_INPUT_OUTCOME = -1;

    // Other game control constants.
    protected static final int MAX_GAMES = 100;
    protected static final int MIN_POSSIBLE_MOVES = 3;
    protected static final int NUM_ROUNDS_TO_DISPLAY = 10;
    protected static final int PERCENTAGE_RESIZE = 100;
    protected static final String QUIT = "q";

    /**
     * Determines if the given move is valid.
     *
     * @param move The player's move to check
     * @return true if the move is valid, false otherwise
     */
    @Override
    public boolean isValidMove(String move) {
        if (move == null) {
            return false;
        }
        for (String validMove : possibleMoves) {
            if (move.equals(validMove)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Plays one round of Rock-Paper-Scissors.
     * Updates the statistics and records the moves if the input is valid.
     *
     * @param playerMove The player's move
     * @param cpuMove    The CPU's move
     */
    @Override
    public void playRPS(String playerMove, String cpuMove) {
        int result = determineWinner(playerMove, cpuMove);

        if (result == PLAYER_WIN_OUTCOME) {
            numPlayerWins++;
            System.out.printf(CPU_MOVE + PLAYER_WIN + "\n", cpuMove);
        } else if (result == CPU_WIN_OUTCOME) {
            numCPUWins++;
            System.out.printf(CPU_MOVE + CPU_WIN + "\n", cpuMove);
        } else if (result == TIE_OUTCOME) {
            numTies++;
            System.out.printf(CPU_MOVE + TIE + "\n", cpuMove);
        }

        // Record valid moves and increment the game count
        if (result != INVALID_INPUT_OUTCOME) {
            playerMoves[numGames] = playerMove;
            cpuMoves[numGames] = cpuMove;
            numGames++;
        }
    }

    /**
     * Generates the next CPU move.
     *
     * @return A randomly selected move from the possible moves
     */
    @Override
    public String genCPUMove() {
        int num = rand.nextInt(possibleMoves.length); // Generate random index
        return possibleMoves[num];
    }

    /**
     * Prints the game statistics, including the most recent games and win percentages.
     */
    @Override
    public void displayStats() {
        float cpuWinPercent = (float) numCPUWins / (float) numGames * PERCENTAGE_RESIZE;
        float playerWinPercent = (float) numPlayerWins / (float) numGames * PERCENTAGE_RESIZE;
        float tiedPercent = (float) numTies / (float) numGames * PERCENTAGE_RESIZE;

        System.out.println(THANKS);

        // Determine how many rounds to display
        int printTo = (numGames < NUM_ROUNDS_TO_DISPLAY) ? 0 : numGames - NUM_ROUNDS_TO_DISPLAY;

        // Print game history
        for (int i = numGames - 1; i >= printTo; i--) {
            System.out.printf(CPU_PLAYER_MOVES, cpuMoves[i], playerMoves[i]);
        }

        // Print overall statistics
        System.out.printf(OVERALL_STATS, cpuWinPercent, playerWinPercent, tiedPercent);
    }
}
