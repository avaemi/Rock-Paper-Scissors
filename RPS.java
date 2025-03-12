/* 
  Name: Ava Emami
  Email: seemami@ucsd.edu
  
*/
import java.util.Scanner;

public class RPS extends RPSAbstract {

    /**
     * Constructs a new instance of RPS with the given possible moves.
     *
     * @param moves All possible moves in the game.
     */
    public RPS(String[] moves) {
        this.possibleMoves = moves;
        this.playerMoves = new String[MAX_GAMES];
        this.cpuMoves = new String[MAX_GAMES];
    }

    public static void main(String[] args) {
        String[] moves = new String[args.length];
        if (args.length >= MIN_POSSIBLE_MOVES) {
            System.arraycopy(args, 0, moves, 0, args.length);
        } else {
            moves = RPS.DEFAULT_MOVES;
        }

        RPS game = new RPS(moves);
        Scanner in = new Scanner(System.in);

        System.out.println(PROMPT_MOVE);

        while (true) {
            // Generate a CPU move for every prompt
            String cpuMove = game.genCPUMove();

            // Read player's input
            String playerMove = in.nextLine().trim();

            // Quit condition
            if (playerMove.equalsIgnoreCase(QUIT)) {
                System.out.println(THANKS);
                game.displayStats();
                break;
            }

            // Handle invalid input
            if (!game.isValidMove(playerMove)) {
                System.out.println(INVALID_INPUT); // Inform the user about invalid move
                System.out.println(PROMPT_MOVE);   // Prompt the user again
                continue;                          // Skip to the next iteration
            }

            // Play the round for valid moves
            game.playRPS(playerMove, cpuMove);

            // Prompt for the next move
            System.out.println(PROMPT_MOVE);
        }

        in.close();
    }

    /**
     * Determines the winner of a Rock-Paper-Scissors round.
     *
     * @param playerMove The player's move.
     * @param cpuMove    The CPU's move.
     * @return -1 for invalid input, 0 for tie, 1 for player win, 2 for CPU win.
     */
    @Override
    public int determineWinner(String playerMove, String cpuMove) {
        if (!isValidMove(playerMove) || !isValidMove(cpuMove)) {
            return INVALID_INPUT_OUTCOME;
        }

        int playerIndex = -1, cpuIndex = -1;
        for (int i = 0; i < possibleMoves.length; i++) {
            if (possibleMoves[i].equals(playerMove)) {
                playerIndex = i;
            }
            if (possibleMoves[i].equals(cpuMove)) {
                cpuIndex = i;
            }
        }

        if (playerIndex == cpuIndex) {
            return TIE_OUTCOME;
        } else if ((playerIndex + 1) % possibleMoves.length == cpuIndex) {
            return CPU_WIN_OUTCOME;
        } else {
            return PLAYER_WIN_OUTCOME;
        }
    }
}
