import java.io.File;
import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class main {
    private static final int TRACK_LENGTH = 50;
    private static final int TELEPORT_COUNT = 10;

    public static void main(String[] args) {
        new main().run();
    }

    private void run() {
        SLL<Car> cars;
        SLL<Track> tracks;
        try {
            String carsPath = "cars.txt";
            String tracksPath = "tracks.txt";
            cars = loadCars(carsPath);
            tracks = loadTracks(tracksPath);
        } catch (FileNotFoundException exception) {
            System.out.println("Data files not found. Please check cars.txt and tracks.txt.");
            return;
        }

        System.out.println("CAR RACE OOP PROJECT");
        System.out.println("Available Cars (sorted by performance):");
        printCars(cars);
        System.out.println();
        System.out.println("Available Tracks:");
        printTracks(tracks);
        System.out.println();

        Scanner input = new Scanner(System.in);
        Random random = new Random();

        Track playerTrack = promptTrackSelection(tracks, input, "Select a track ID for Race 1a: ");
        System.out.println("Remaining Tracks:");
        printTracks(tracks);
        System.out.println();

        Car playerCar = promptCarSelection(cars, input, "Select a car ID for Race 1a: ");
        Car computerCar = selectBestOpponent(cars, playerTrack, playerCar);
        System.out.println("Computer selected car: " + computerCar.name);
        System.out.println();

        SLL<RaceResult> raceLog = new SLL<>();

        RaceResult race1a = runRace("Race 1a", playerTrack, playerCar, computerCar, random);
        raceLog.append(race1a);
        printRaceSummary(race1a);
        System.out.println();

        Track race1bTrack = selectRandomTrack(tracks, random);
        System.out.println("Race 1b track selected by computer: " + race1bTrack.name);
        System.out.println("Remaining Tracks:");
        printTracks(tracks);
        System.out.println();

        Car computer1 = selectRandomCar(cars, random);
        Car computer2 = selectRandomCar(cars, random);
        RaceResult race1b = runRace("Race 1b", race1bTrack, computer1, computer2, random);
        raceLog.append(race1b);
        printRaceSummary(race1b);
        System.out.println();

        Track finalTrack;
        if (race1a.winner == playerCar) {
            finalTrack = promptTrackSelection(tracks, input, "Select a track ID for the Final race: ");
        } else {
            finalTrack = selectRandomTrack(tracks, random);
            System.out.println("Computer selected final track: " + finalTrack.name);
        }
        System.out.println("Remaining Tracks:");
        printTracks(tracks);
        System.out.println();

        RaceResult finalRace = runRace("Final", finalTrack, race1a.winner, race1b.winner, random);
        raceLog.append(finalRace);
        printRaceSummary(finalRace);
        System.out.println();

        System.out.println("Race Log:");
        printRaceLog(raceLog);
        input.close();
    }

    private SLL<Car> loadCars(String path) throws FileNotFoundException {
        SLL<Car> cars = new SLL<>();
        Scanner scanner = new Scanner(new File(path));
        scanner.useDelimiter(",|\n");

        int skipped = 0;
        while (scanner.hasNext() && skipped < 6) {
            scanner.next();
            skipped++;
        }

        while (scanner.hasNext()) {
            String idToken = nextToken(scanner);
            if (idToken == null) {
                break;
            }
            String name = nextToken(scanner);
            String powerToken = nextToken(scanner);
            String controlToken = nextToken(scanner);
            String speedToken = nextToken(scanner);
            String type = nextToken(scanner);
            if (name == null || powerToken == null || controlToken == null || speedToken == null || type == null) {
                break;
            }
            int id = Integer.parseInt(idToken);
            int power = Integer.parseInt(powerToken);
            int control = Integer.parseInt(controlToken);
            int speed = Integer.parseInt(speedToken);
            int performance = power + control + speed;
            cars.insertSorted(new Car(id, name, performance, type), new CarPerformanceComparator());
        }

        scanner.close();
        return cars;
    }

    private SLL<Track> loadTracks(String path) throws FileNotFoundException {
        SLL<Track> tracks = new SLL<>();
        Scanner scanner = new Scanner(new File(path));
        scanner.useDelimiter(",|\n");

        int skipped = 0;
        while (scanner.hasNext() && skipped < 5) {
            scanner.next();
            skipped++;
        }

        while (scanner.hasNext()) {
            String idToken = nextToken(scanner);
            if (idToken == null) {
                break;
            }
            String name = nextToken(scanner);
            String type = nextToken(scanner);
            String difficultyToken = nextToken(scanner);
            String boostToken = nextToken(scanner);
            if (name == null || type == null || difficultyToken == null || boostToken == null) {
                break;
            }
            int id = Integer.parseInt(idToken);
            int boost = Integer.parseInt(boostToken);
            tracks.append(new Track(id, name, type, boost));
        }

        scanner.close();
        return tracks;
    }

    private String nextToken(Scanner scanner) {
        while (scanner.hasNext()) {
            String token = scanner.next();
            if (token != null) {
                token = token.trim();
                if (!token.isEmpty()) {
                    return token;
                }
            }
        }
        return null;
    }

    private void printCars(SLL<Car> cars) {
        SLLnode<Car> current = cars.getHead();
        while (current != null) {
            Car car = current.data;
            System.out.println("ID: " + car.id + " | " + car.name + " | Performance: " + car.performance
                    + " | Type: " + car.type);
            current = current.next;
        }
    }

    private void printTracks(SLL<Track> tracks) {
        SLLnode<Track> current = tracks.getHead();
        while (current != null) {
            Track track = current.data;
            System.out.println("ID: " + track.id + " | " + track.name + " | Type: " + track.type
                    + " | Boost: " + track.boost);
            current = current.next;
        }
    }

    private Track promptTrackSelection(SLL<Track> tracks, Scanner input, String prompt) {
        Track selected = null;
        while (selected == null) {
            int id = readInt(input, prompt);
            selected = tracks.removeFirst(track -> track.id == id);
            if (selected == null) {
                System.out.println("Invalid track ID. Try again.");
            }
        }
        return selected;
    }

    private Car promptCarSelection(SLL<Car> cars, Scanner input, String prompt) {
        Car selected = null;
        while (selected == null) {
            int id = readInt(input, prompt);
            selected = cars.removeFirst(car -> car.id == id);
            if (selected == null) {
                System.out.println("Invalid car ID. Try again.");
            }
        }
        return selected;
    }

    private int readInt(Scanner input, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = input.nextLine();
            if (line != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    try {
                        return Integer.parseInt(line);
                    } catch (NumberFormatException ignored) {
                        System.out.println("Please enter a valid number.");
                    }
                }
            }
        }
    }

    private Car selectBestOpponent(SLL<Car> cars, Track track, Car playerCar) {
        Car bestCar = null;
        int bestScore = Integer.MIN_VALUE;
        SLLnode<Car> current = cars.getHead();
        while (current != null) {
            Car car = current.data;
            int score = calculateInitialScore(car, track, playerCar);
            if (score > bestScore) {
                bestScore = score;
                bestCar = car;
            }
            current = current.next;
        }
        if (bestCar == null) {
            return null;
        }
        int bestCarId = bestCar.id;
        cars.removeFirst(car -> car.id == bestCarId);
        return bestCar;
    }

    private Car selectRandomCar(SLL<Car> cars, Random random) {
        int index = random.nextInt(cars.size());
        return cars.removeAt(index);
    }

    private Track selectRandomTrack(SLL<Track> tracks, Random random) {
        int index = random.nextInt(tracks.size());
        return tracks.removeAt(index);
    }

    private RaceResult runRace(String roundName, Track track, Car car1, Car car2, Random random) {
        int car1Score = calculateInitialScore(car1, track, car2);
        int car2Score = calculateInitialScore(car2, track, car1);

        RaceCar racer1 = new RaceCar(car1, car1Score);
        RaceCar racer2 = new RaceCar(car2, car2Score);

        DLL<TrackUnit> raceTrack = buildRaceTrack(random);

        while (!racer1.finished && !racer2.finished) {
            moveCar(racer1, raceTrack, random);
            moveCar(racer2, raceTrack, random);
            if (racer1.finished || racer2.finished) {
                break;
            }
        }

        Car winner = determineWinner(racer1, racer2);
        return new RaceResult(roundName, track, racer1, racer2, winner);
    }

    private DLL<TrackUnit> buildRaceTrack(Random random) {
        DLL<TrackUnit> track = new DLL<>();
        int position = 1;
        while (position <= TRACK_LENGTH) {
            track.append(new TrackUnit(position));
            position++;
        }

        int teleports = 0;
        while (teleports < TELEPORT_COUNT) {
            int location = randomBetween(random, 1, TRACK_LENGTH);
            TrackUnit unit = track.getAt(location - 1);
            if (unit != null && !unit.isTeleport && !unit.isReset) {
                unit.isTeleport = true;
                teleports++;
            }
        }

        while (true) {
            int location = randomBetween(random, 1, TRACK_LENGTH);
            TrackUnit unit = track.getAt(location - 1);
            if (unit != null && !unit.isTeleport) {
                unit.isReset = true;
                break;
            }
        }

        return track;
    }

    private void moveCar(RaceCar racer, DLL<TrackUnit> track, Random random) {
        if (racer.finished) {
            return;
        }
        int move = randomBetween(random, 1, 3);
        racer.position += move;
        racer.score -= move * 5;
        if (racer.position > TRACK_LENGTH) {
            racer.position = TRACK_LENGTH;
        }
        racer.iterations++;

        if (racer.position >= TRACK_LENGTH || racer.score <= 0) {
            racer.finished = true;
            return;
        }

        TrackUnit unit = track.getAt(racer.position - 1);
        if (unit != null) {
            if (unit.isReset) {
                racer.position = 1;
                racer.score -= 5;
            } else if (unit.isTeleport) {
                int delta = randomBetween(random, 1, 5);
                if (random.nextBoolean()) {
                    racer.position += delta;
                } else {
                    racer.position -= delta;
                }
                if (racer.position < 1) {
                    racer.position = 1;
                }
                if (racer.position > TRACK_LENGTH) {
                    racer.position = TRACK_LENGTH;
                }
                racer.score -= 5;
            }
        }

        if (racer.position >= TRACK_LENGTH || racer.score <= 0) {
            racer.finished = true;
        }
    }

    private Car determineWinner(RaceCar racer1, RaceCar racer2) {
        boolean racer1Finished = racer1.position >= TRACK_LENGTH;
        boolean racer2Finished = racer2.position >= TRACK_LENGTH;

        if (racer1Finished && racer2Finished) {
            if (racer1.iterations != racer2.iterations) {
                return racer1.iterations < racer2.iterations ? racer1.car : racer2.car;
            }
            if (racer1.score != racer2.score) {
                return racer1.score > racer2.score ? racer1.car : racer2.car;
            }
            return racer1.car;
        }

        if (racer1Finished) {
            return racer1.car;
        }
        if (racer2Finished) {
            return racer2.car;
        }

        if (racer1.score <= 0 && racer2.score <= 0) {
            if (racer1.iterations != racer2.iterations) {
                return racer1.iterations < racer2.iterations ? racer1.car : racer2.car;
            }
            return racer1.score >= racer2.score ? racer1.car : racer2.car;
        }

        if (racer1.score <= 0) {
            return racer2.car;
        }
        return racer1.car;
    }

    private void printRaceSummary(RaceResult result) {
        System.out.println("[" + result.round + " | " + result.track.name + " | " + result.car1.name + ":"
                + result.car1Score + " (" + result.car1Iterations + ")" + " vs " + result.car2.name + ":"
                + result.car2Score + " (" + result.car2Iterations + ")" + " | WINNER: " + result.winner.name + "]");
    }

    private void printRaceLog(SLL<RaceResult> log) {
        SLLnode<RaceResult> current = log.getHead();
        while (current != null) {
            printRaceSummary(current.data);
            current = current.next;
            if (current != null) {
                System.out.println("  ↓");
            }
        }
    }

    private int calculateInitialScore(Car car, Track track, Car opponent) {
        int score = car.performance;
        if (car.type.equalsIgnoreCase(track.type)) {
            score += track.boost;
        }
        score += matchupBonus(car.type, opponent.type);
        return score;
    }

    private int matchupBonus(String typeA, String typeB) {
        if (typeA.equalsIgnoreCase("Electric") && typeB.equalsIgnoreCase("Water")) {
            return 15;
        }
        if (typeA.equalsIgnoreCase("Water") && typeB.equalsIgnoreCase("Fire")) {
            return 15;
        }
        if (typeA.equalsIgnoreCase("Fire") && typeB.equalsIgnoreCase("Earth")) {
            return 15;
        }
        if (typeA.equalsIgnoreCase("Earth") && typeB.equalsIgnoreCase("Electric")) {
            return 15;
        }
        if (typeA.equalsIgnoreCase("Air") && typeB.equalsIgnoreCase("Earth")) {
            return 10;
        }
        if (typeA.equalsIgnoreCase("Heavy") && typeB.equalsIgnoreCase("Air")) {
            return 10;
        }
        return 0;
    }

    private int randomBetween(Random random, int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    private static class Car {
        private final int id;
        private final String name;
        private final int performance;
        private final String type;

        private Car(int id, String name, int performance, String type) {
            this.id = id;
            this.name = name;
            this.performance = performance;
            this.type = type;
        }
    }

    private static class Track {
        private final int id;
        private final String name;
        private final String type;
        private final int boost;

        private Track(int id, String name, String type, int boost) {
            this.id = id;
            this.name = name;
            this.type = type;
            this.boost = boost;
        }
    }

    private static class RaceCar {
        private final Car car;
        private int position;
        private int score;
        private int iterations;
        private boolean finished;

        private RaceCar(Car car, int initialScore) {
            this.car = car;
            this.position = 1;
            this.score = initialScore;
        }
    }

    private static class TrackUnit {
        private final int position;
        private boolean isTeleport;
        private boolean isReset;

        private TrackUnit(int position) {
            this.position = position;
        }
    }

    private static class RaceResult {
        private final String round;
        private final Track track;
        private final Car car1;
        private final Car car2;
        private final int car1Score;
        private final int car2Score;
        private final int car1Iterations;
        private final int car2Iterations;
        private final Car winner;

        private RaceResult(String round, Track track, RaceCar racer1, RaceCar racer2, Car winner) {
            this.round = round;
            this.track = track;
            this.car1 = racer1.car;
            this.car2 = racer2.car;
            this.car1Score = racer1.score;
            this.car2Score = racer2.score;
            this.car1Iterations = racer1.iterations;
            this.car2Iterations = racer2.iterations;
            this.winner = winner;
        }
    }

    private static class CarPerformanceComparator implements Comparator<Car> {
        @Override
        public int compare(Car first, Car second) {
            return Integer.compare(first.performance, second.performance);
        }
    }
}
