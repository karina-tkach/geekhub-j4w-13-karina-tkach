package org.geekhub.ciphers;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.geekhub.ciphers.CipherUtil.getKeyLetter;

public class A1Z26Cipher implements Cipher {
    private static final int ASCII_NUMBER_OF_FIRST_LETTER = 65;

    @Override
    public String encrypt(String message) {
        AtomicBoolean lastCharIsLetter = new AtomicBoolean(false);
        return message.chars()
            .mapToObj(letter -> getStringForA1Z26Encryption(letter, lastCharIsLetter))
            .collect(Collectors.joining());
    }

    @Override
    public String decrypt(String message) {
        StringBuilder decryptedMessage = new StringBuilder();
        String[] groups = message.split("\\s+");
        for (String group : groups) {
            String[] elements = group.split("-");
            for (String element : elements) {
                if (element.matches("\\d+")) {
                    decryptedMessage.append((char) (Integer.parseInt(element) + ASCII_NUMBER_OF_FIRST_LETTER - 1));
                    continue;
                }

                Pattern pattern = Pattern.compile("(\\d+)?(\\D+)(\\d+)?");
                Matcher matcher = pattern.matcher(element);
                String firstGroup = null;
                String secondGroup = null;
                String thirdGroup = null;

                while (matcher.find()) {
                    firstGroup = matcher.group(1);
                    secondGroup = matcher.group(2);
                    thirdGroup = matcher.group(3);
                }

                if (firstGroup != null) {
                    decryptedMessage.append((char) (Integer.parseInt(firstGroup) + ASCII_NUMBER_OF_FIRST_LETTER - 1));
                }
                decryptedMessage.append(secondGroup);
                if (thirdGroup != null) {
                    decryptedMessage.append((char) (Integer.parseInt(thirdGroup) + ASCII_NUMBER_OF_FIRST_LETTER - 1));
                }

            }
            decryptedMessage.append(" ");
        }
        return decryptedMessage.toString().trim();
    }

    @Override
    public String getCipherName() {
        return "A1Z26";
    }

    private String getStringForA1Z26Encryption(int letter, AtomicBoolean lastCharIsLetter) {
        int keyLetter = getKeyLetter(letter);
        if (keyLetter == 0) {
            lastCharIsLetter.set(false);
            return String.valueOf((char) letter);
        }

        String result = String.valueOf(letter - keyLetter + 1);
        if (lastCharIsLetter.get()) {
            result = "-" + result;
        }
        lastCharIsLetter.set(true);
        return result;
    }
}
