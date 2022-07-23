import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.lang.*;

public class Program {
    public void readFile() throws IOException {
        FileReader fr = new FileReader("file1.txt");
        Scanner scanner = new Scanner(fr);
        HashMap<String, String> nameAndPhone = new HashMap<>();
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            nameAndPhone.put(s.split(" ")[0], s.split(" ")[1]);
        }
        Set<String> keys = nameAndPhone.keySet();
        FileWriter fw = new FileWriter("file2.txt");
        for (String key : keys) {
            if (nameAndPhone.get(key).chars().distinct().count() <= 3) {
                fw.write(key + " " + nameAndPhone.get(key) + "\n");
                System.out.println("тут");
            }
        }
        fw.close();
        scanner.close();
    }

    void convertBinary() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите число ");
        Integer number = scanner.nextInt();
        String inputBinary = Integer.toBinaryString(number);
        int countZero=0;
        int countOne = 0;
        for (char element : inputBinary.toCharArray()){
            if (element == '0') countZero++;
            if (element == '1') countOne++;
        }
        System.out.println(inputBinary);
        System.out.println(inputBinary.length());
        char[] binaryArray = inputBinary.toCharArray();
        int[] intArray = new int[binaryArray.length];
        String[] strings = new String[binaryArray.length];
        for (int i = 0; i < binaryArray.length; i++) {
            strings[i] = String.valueOf(binaryArray[i]);
            System.out.print(strings[i]);
        }
        System.out.println();
        StringBuilder resultBinary = new StringBuilder();
        for (int i = 0; i < strings.length; i++) {
            intArray[i] = Integer.parseInt(strings[i], 2);
            if (i % 2 == 0) intArray[i] = ~intArray[i];
            if (i > 0) {
                resultBinary.append(intArray[i]);
            }
            System.out.print(strings[i] + " ");
            System.out.print(intArray[i] + " ");
        }
//        System.out.println();
//        System.out.println("результат: " + resultBinary);
//        System.out.println(Integer.parseInt(resultBinary.toString()));
    }

    public void sort() {
        int[] array = new int[10000];
        for (int i = 0; i < array.length; i++) {
            Random rnd = new Random();
            array[i] = rnd.nextInt(2001) - 1000;
        }
        int[] arrayBubble = new int[10000];
        arrayBubble = array.clone();
        bubbleSort(arrayBubble);
        insertionSort(array);
    }
    private void bubbleSort(int[] array) {

        boolean needIteration = true;
        int step = 0;
        while (needIteration) {
            needIteration = false;
            for (int i = 1; i < array.length; i++) {
                step++;
                if (array[i] < array[i - 1]) {
                    int swap = array[i];
                    array[i] = array[i-1];
                    array[i-1] = swap;
                    needIteration = true;
                }
            }
        }
        System.out.println("Сортировка пузырьком");
        System.out.println(Arrays.toString(array));
        System.out.println("Количество затраченных шагов: " + step);
    }

    private void insertionSort(int[] array) {
        int step = 0;
        for (int left = 0; left < array.length; left++) {
            int value = array[left]; // Вытаскиваем значение элемента
            int i = left - 1; // Перемещаемся по элементам, которые перед вытащенным элементом
            for (; i >= 0; i--) {
                step++;
                if (value < array[i]) { // Если вытащили значение меньшее — передвигаем больший элемент дальше
                    array[i + 1] = array[i];
                } else { // Если вытащенный элемент больше — останавливаемся
                    break;
                }
            }
            array[i + 1] = value; // В освободившееся место вставляем вытащенное значение
        }
        System.out.println("Сортировка вставками");
        System.out.println(Arrays.toString(array));
        System.out.println("Количество затраченных шагов: " + step);
    }

    public void parseString() {
        Scanner scanner = new Scanner(System.in);
        String[] words = scanner.nextLine().split(", ");
        Character[] vowels = new Character[]{ 'а','у','о','ы','э','я','ю','ё','и','е'};
        ArrayList<Character> vowelsList = new ArrayList<>(Arrays.asList(vowels));
        int countVowels = 0;
        for (String word : words) {
            word = word.toLowerCase();
            for (char letter : word.toCharArray()) {
                if (vowelsList.contains(letter)) countVowels++;
            }
            int consonants = word.length() - countVowels;
            word = word.substring(0,1).toUpperCase() + word.substring(1) + " " + countVowels + " " + consonants + " ";
            System.out.print(word);
            countVowels = 0;
        }
        scanner.close();
    }


}
