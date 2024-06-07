package controllers;
import java.text.DecimalFormat;

public class NumberToWords {

    private static final String[] tensNames = {
        "",
        " dix",
        " vingt",
        " trente",
        " quarante",
        " cinquante",
        " soixante",
        " soixante-dix",
        " quatre-vingt",
        " quatre-vingt-dix"
    };

    private static final String[] numNames = {
        "",
        " un",
        " deux",
        " trois",
        " quatre",
        " cinq",
        " six",
        " sept",
        " huit",
        " neuf",
        " dix",
        " onze",
        " douze",
        " treize",
        " quatorze",
        " quinze",
        " seize",
        " dix-sept",
        " dix-huit",
        " dix-neuf"
    };

    private static String convertLessThanOneThousand(int number) {
        String current;

        if (number % 100 < 20) {
            current = numNames[number % 100];
            number /= 100;
        } else {
            current = numNames[number % 10];
            number /= 10;

            current = tensNames[number % 10] + current;
            number /= 10;
        }
        if (number == 0) return current;
        return numNames[number] + " cent" + current;
    }

    public static String convert(long number) {
        // 0 à 999 999 999 999
        if (number == 0) { return "zéro"; }

        String snumber = Long.toString(number);

        // pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        // XXXnnnnnnnnn
        int billions = Integer.parseInt(snumber.substring(0,3));
        // nnnXXXnnnnnn
        int millions  = Integer.parseInt(snumber.substring(3,6));
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(snumber.substring(6,9));
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9,12));

        String tradBillions;
        switch (billions) {
        case 0:
            tradBillions = "";
            break;
        case 1 :
            tradBillions = convertLessThanOneThousand(billions)
            + " milliard ";
            break;
        default :
            tradBillions = convertLessThanOneThousand(billions)
            + " milliards ";
        }
        String result =  tradBillions;

        String tradMillions;
        switch (millions) {
        case 0:
            tradMillions = "";
            break;
        case 1 :
            tradMillions = convertLessThanOneThousand(millions)
               + " million ";
            break;
        default :
            tradMillions = convertLessThanOneThousand(millions)
               + " millions ";
        }
        result =  result +  tradMillions;

        String tradHundredThousands;
        switch (hundredThousands) {
        case 0:
            tradHundredThousands = "";
            break;
        case 1 :
            tradHundredThousands = "mille ";
            break;
        default :
            tradHundredThousands = convertLessThanOneThousand(hundredThousands)
               + " mille ";
        }
        result =  result + tradHundredThousands;

        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result =  result + tradThousand;

        // remove extra spaces!
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }

}
