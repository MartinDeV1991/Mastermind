import java.util.*;

public class Mastermind {
    private static final int MAX_ATTEMPTS = 12;
    private static final int CODE_LENGTH = 4;
    private static final int MAX_PLAYERS = 10;
    private static final char[] COLORS = { 'A', 'B', 'C', 'D', 'E', 'F' };

    public static void main(String[] args) {
        System.out.println("-------------------------------------------------- ");
        System.out.println("-----Welkom, dit is mastermind!");
        System.out.println("-----De computer verzint een code die bestaat uit " + CODE_LENGTH + " kleuren.");
        System.out.println("-----Je kunt kiezen uit: A, B, C, D, E, F");
        System.out.println("-----Een kleur kan meerdere keren voorkomen in de code.");

        Scanner scanner = new Scanner(System.in);
        ArrayList<String> players = getPlayerNames(scanner);
        int numberOfPlayers = players.size();
        int[] score = new int[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            score[i] = 0;
        }
        boolean end = false;
        int winners = 0;
        char[] code = CreateCode();
        String guess;
        System.out.println(code);
        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            if (end == false) {
                System.out.println("-------------------------------------------------- ");
                System.out.println("Poging " + attempt + " van " + MAX_ATTEMPTS);
                for (int i = 0; i < numberOfPlayers; i++) {
                    if (score[i] == 0 && end == false) {
                        System.out.println(players.get(i) + ", voer je code in:");

                        do {
                            guess = scanner.nextLine();
                            guess = guess.toUpperCase();
                            if (guess.equals("Q")) {
                                System.out.println("Het spel wordt afgesloten!");
                                end = true;
                                break;
                            }
                            if (!checkInput(guess)) {
                                System.out.println("Deze input is niet toegestaan, probeer het opnieuw!");
                            }
                        } while (!checkInput(guess));

                        if (end == true) {
                            break;
                        }

                        int[] pegs = checkGuess(code, guess.toCharArray());
                        if (pegs[0] == 4) {
                            System.out.println("Goed gedaan! Je hebt de code geraden: ");
                            System.out.println(code);
                            score[i] = attempt;
                            winners++;
                        }
                    }
                }
                if (winners == numberOfPlayers) {
                    end = true;
                }
            }
        }
        if (winners == 0) {
            System.out.println("Helaas! Niemand heeft de code geraden.");
        } else {
            for (int i = 0; i < numberOfPlayers; i++) {
                if (score[i] > 0) {
                    System.out.println("De code is geraden in " + score[i] + " beurten door " + players.get(i));
                }
            }

        }
        scanner.close();
    }

    private static ArrayList<String> getPlayerNames(Scanner scanner) {
        ArrayList<String> players = new ArrayList<>();
        for (int i = 1; i <= MAX_PLAYERS; i++) {
            System.out
                    .println("-----Vul de naam in van speler " + i + ". Type 'start' als je alle spelers toegevoegd hebt.");
            String name = scanner.nextLine();
            if (name.equals("start")) {
                break;
            }
            players.add(name);
        }
        return players;
    }

    private static char[] CreateCode() {
        char[] code = new char[4];
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int randomColor = random.nextInt(COLORS.length);
            code[i] = COLORS[randomColor];
        }
        return code;
    }

    private static boolean checkInput(String guess) {
        if (guess.length() != CODE_LENGTH) {
            return false;
        }
        return true;
    }

    private static int[] checkGuess(char[] code1, char[] guess1) {
        char[] code = Arrays.copyOf(code1, code1.length);
        char[] guess = Arrays.copyOf(guess1, guess1.length);
        int blackPegs = 0;
        int whitePegs = 0;
        for (int i = 0; i < code.length; i++) {
            if (guess[i] == code[i]) {
                blackPegs++;
                code[i] = 'X';
                guess[i] = 'Y';
            }
        }
        for (int i = 0; i < code.length; i++) {
            for (int j = 0; j < guess.length; j++) {
                if (code[i] == guess[j]) {
                    whitePegs++;
                    code[i] = 'X';
                    guess[j] = 'Y';
                }
            }
        }
        String plusSigns = "+".repeat(blackPegs);
        String questionMarks = "?".repeat(whitePegs);
        System.out.println("Jouw code: " + new String(guess1) + " | feedback: " + plusSigns + questionMarks);

        int[] pegs = new int[2];
        pegs[0] = blackPegs;
        pegs[1] = whitePegs;
        return pegs;
    }
}
