import java.util.*;

public class Driver
{
    public static void main(String[] args)
    {
        if (args.length < 2 ||
            args[0].equals("--help") ||
            args[0].equals("-h"))
        {
            printHelp();
        }
        else
        {
            try
            {
                menu(args);
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
                System.out.println("Exiting...");
            }
        }
    }

    public static void menu(String[] files)
    {
        Menu menu;
        boolean exit;
        DSALinkedList<Nominee> nomineesList;
        DSALinkedList<HousePreference> prefList;

        menu = new Menu(6);
        menu.addOption("Please select an option:");
        menu.addOption("    1. List Nominees");
        menu.addOption("    2. Nominee Search");
        menu.addOption("    3. List by Margin");
        menu.addOption("    4. Itinerary by Margin");
        menu.addOption("    5. Exit");
        exit = false;

        nomineesList = processNominees(files[0]);
        prefList = processHousePreference(files[1]);

        while (! exit)
        {
            try
            {
                switch (menu.getUserInput())
                {
                    case 1:
                        listNominees(nomineesList);
                        break;
                    case 2:
                        searchNominees(nomineesList);
                        break;
                    case 3:
                        System.out.println("Selected List by Margin");
                        break;
                    case 4:
                        System.out.println("Selected Itinerary by Margin");
                        break;
                    case 5:
                        System.out.println("Selected Exit");
                        System.out.println("Exiting...");
                        exit = true;
                        break;
                }
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
    }

    public static DSALinkedList<Nominee> processNominees(String file)
    {
        DSALinkedList<String> list;
        DSALinkedList<Nominee> nomineeList;
        Iterator<String> iter;
        DSALinkedList<String> invalidEntries;
        String line;

        list = FileIO.readText(file);
        iter = list.iterator();

        nomineeList = new DSALinkedList<Nominee>();
        invalidEntries = new DSALinkedList<String>();
        line = "";

        if (iter.hasNext())
        {
            iter.next();

            while (iter.hasNext())
            {
                try
                {
                    line = iter.next();
                    nomineeList.insertLast(new Nominee(line));
                }
                catch (IllegalArgumentException e)
                {
                    invalidEntries.insertLast(line);
                }
            }
        }
        else
        {
            throw new IllegalArgumentException(
                "House candidate file is invalid"
            );
        }

        iter = invalidEntries.iterator();

        while (iter.hasNext())
        {
            System.out.println(
                "Invalid entry in " + file +
                ": " + iter.next()
            );
        }

        return nomineeList;
    }

    public static DSALinkedList<HousePreference> processHousePreference(
            String file)
    {
        DSALinkedList<String> list;
        DSALinkedList<HousePreference> prefList;
        Iterator<String> iter;
        DSALinkedList<String> invalidEntries;
        String line;

        list = FileIO.readText(file);
        iter = list.iterator();

        prefList = new DSALinkedList<HousePreference>();
        invalidEntries = new DSALinkedList<String>();
        line = "";

        if (iter.hasNext())
        {
            iter.next();

            while (iter.hasNext())
            {
                try
                {
                    line = iter.next();
                    prefList.insertLast(new HousePreference(line));
                }
                catch (IllegalArgumentException e)
                {
                    invalidEntries.insertLast(line);
                }
            }
        }
        else
        {
            throw new IllegalArgumentException(
                "House preference file is invalid"
            );
        }

        iter = invalidEntries.iterator();

        while (iter.hasNext())
        {
            System.out.println(
                "Invalid entry in " + file +
                ": " + iter.next()
            );
        }

        return prefList;
    }

    public static void listNominees(DSALinkedList<Nominee> nomineeList)
    {
        Menu filterMenu;
        Menu orderMenu;
        Iterator<Nominee> iter;
        Nominee inList;
        Nominee searchResult[];

        iter = nomineeList.iterator();

        boolean filterOptions[] = {false, false, false};
        boolean orderOptions[] = {false, false, false};

        String userInput;
        String[] split;
        int optionSwitch;

        String stateFilter = "";
        String partyFilter = "";
        String divisionFilter = "";

        int numMatches = 0;
        int numNominee = 0;
        int i = 0;

        filterMenu = new Menu(4);
        orderMenu = new Menu(4);

        filterMenu.addOption("Select filter type (eg: 1 2 3):");
        filterMenu.addOption("    1. State");
        filterMenu.addOption("    2. Party");
        filterMenu.addOption("    3. Division");

        orderMenu.addOption("Select order type (eg: 1 2 3):");
        orderMenu.addOption("    1. Surname");
        orderMenu.addOption("    2. State");
        orderMenu.addOption("    3. Party");

        filterMenu.printMenu();
        userInput = Input.string();
        split = userInput.split(" ");

        if (! userInput.isEmpty())
        {
            for (String option : split)
            {
                try
                {
                    optionSwitch = Integer.parseInt(option);
                    if (optionSwitch > 0 && optionSwitch < 4)
                    {
                        filterOptions[optionSwitch - 1] = true;
                    }
                }
                catch (NumberFormatException e)
                {
                    System.out.println(
                        "Invalid option: " + option + ". Ignoring"
                    );
                }
            }
        }

        if (filterOptions[0])
        {
            stateFilter = Input.string("Input state filter: ");

            if (stateFilter.isEmpty())
            {
                stateFilter = ".*";
            }
        }
        else
        {
            stateFilter = ".*";
        }

        if (filterOptions[1])
        {
            partyFilter = Input.string("Input party filter: ");

            if (partyFilter.isEmpty())
            {
                partyFilter = ".*";
            }
        }
        else
        {
            partyFilter = ".*";
        }

        if (filterOptions[2])
        {
            divisionFilter = Input.string("Input division filter: ");

            if (divisionFilter.isEmpty())
            {
                divisionFilter = ".*";
            }
        }
        else
        {
            divisionFilter = ".*";
        }

        orderMenu.printMenu();
        userInput = Input.string();
        split = userInput.split(" ");

        if (! userInput.isEmpty())
        {
            for (String option : split)
            {
                try
                {
                    optionSwitch = Integer.parseInt(option);
                    if (optionSwitch > 0 && optionSwitch < 4)
                    {
                        orderOptions[optionSwitch - 1] = true;
                    }
                }
                catch (NumberFormatException e)
                {
                    System.out.println(
                        "Invalid option: " + option + ". Ignoring"
                    );
                }
            }
        }

        while (iter.hasNext())
        {
            numNominee++;
            inList = iter.next();
            if (inList.getState().matches(stateFilter) &&
                inList.getNameParty().matches(partyFilter) &&
                inList.getNameDivision().matches(divisionFilter))
            {
                numMatches++;
            }
        }

        searchResult = new Nominee[numMatches];
        iter = null;
        inList = null;
        iter = nomineeList.iterator();

        while (iter.hasNext())
        {
            inList = iter.next();
            if (inList.getState().matches(stateFilter) &&
                inList.getNameParty().matches(partyFilter) &&
                inList.getNameDivision().matches(divisionFilter))
            {
                searchResult[i] = inList;
                i++;
            }
        }

        printNomineesTable(searchResult);
        System.out.println("\n" + numMatches + "/" + numNominee + " matches");
    }

    public static void searchNominees(DSALinkedList<Nominee> nomineeList)
    {
        Menu filterMenu;
        Iterator<Nominee> iter;
        Nominee inList;
        Nominee searchResult[];

        iter = nomineeList.iterator();

        boolean filterOptions[] = {false, false};

        String userInput;
        String[] split;
        int optionSwitch;

        String surnameFilter = "";
        String stateFilter = "";
        String partyFilter = "";

        int numMatches = 0;
        int numNominee = 0;
        int i = 0;

        filterMenu = new Menu(3);

        filterMenu.addOption("Select filter type (eg: 1 2 3):");
        filterMenu.addOption("    1. State");
        filterMenu.addOption("    2. Party");

        surnameFilter = Input.string("Input surname search term: ");

        filterMenu.printMenu();
        userInput = Input.string();
        split = userInput.split(" ");

        if (! userInput.isEmpty())
        {
            for (String option : split)
            {
                try
                {
                    optionSwitch = Integer.parseInt(option);
                    if (optionSwitch > 0 && optionSwitch < 4)
                    {
                        filterOptions[optionSwitch - 1] = true;
                    }
                }
                catch (NumberFormatException e)
                {
                    System.out.println(
                        "Invalid option: " + option + ". Ignoring"
                    );
                }
            }
        }

        if (filterOptions[0])
        {
            stateFilter = Input.string("Input state filter: ");

            if (stateFilter.isEmpty())
            {
                stateFilter = ".*";
            }
        }
        else
        {
            stateFilter = ".*";
        }

        if (filterOptions[1])
        {
            partyFilter = Input.string("Input party filter: ");

            if (partyFilter.isEmpty())
            {
                partyFilter = ".*";
            }
        }
        else
        {
            partyFilter = ".*";
        }

        while (iter.hasNext())
        {
            inList = iter.next();
            if (inList.getSurname().startsWith(surnameFilter) &&
                inList.getState().matches(stateFilter) &&
                inList.getNameParty().matches(partyFilter))
            {
                numMatches++;
            }
        }

        searchResult = new Nominee[numMatches];
        iter = null;
        inList = null;
        iter = nomineeList.iterator();

        while (iter.hasNext())
        {
            numNominee++;
            inList = iter.next();
            if (inList.getSurname().startsWith(surnameFilter) &&
                inList.getState().matches(stateFilter) &&
                inList.getNameParty().matches(partyFilter))
            {
                searchResult[i] = inList;
                i++;
            }
        }

        printNomineesTable(searchResult);
        System.out.println("\n" + numMatches + "/" + numNominee + " matches");
    }

    public static void printNomineesTable(Nominee[] nomineeArr)
    {
        String padding, headerStr, sep, out;

        String header[] = {
            "StateAb", "DivisionID", "DivisionNm", "PartyAb", "PartyNm",
            "CandidateID", "Surname", "GivenNm", "Elected", "HistoricElected"
        };

        String fields[][] = new String[nomineeArr.length][header.length];
        String[] tempArr = new String[nomineeArr.length + 1];

        int paddingArr[] = new int[header.length];

        padding = "";
        headerStr = "";
        sep = "";
        out = "";

        for (int i = 0; i < nomineeArr.length; i++)
        {
            fields[i] = nomineeArr[i].toString().split(",");
        }

        for (int i = 0; i < paddingArr.length; i++)
        {
            tempArr[0] = header[i];

            for (int j = 0; j < nomineeArr.length; j++)
            {
                tempArr[j + 1] = fields[j][i];
            }

            paddingArr[i] = calcMaxStringArrLenght(tempArr);
        }


        for (int i = 0; i < paddingArr.length - 1; i++)
        {
            padding = "%-" + (paddingArr[i] + 2) + "s";
            sep += "+" + formatPadding(padding, '-');
            padding = "%-" + paddingArr[i] + "s";
            headerStr += "| " + String.format(padding, header[i]) + " ";
        }

        padding = "%-" + (paddingArr[paddingArr.length - 1] + 2) + "s";
        sep += "+" + formatPadding(padding, '-') + "+";
        padding = "%-" + paddingArr[paddingArr.length - 1] + "s";
        headerStr += "| " + String.format(padding, header[header.length - 1])
                  + " |";

        for (int i = 0; i < fields.length; i++)
        {
            for (int j = 0; j < fields[i].length - 1; j++)
            {
                padding = "%-" + paddingArr[j] + "s";
                out += "| " + String.format(padding, fields[i][j]) + " ";
            }

            padding = "%-" + paddingArr[paddingArr.length - 1] + "s";
            out += "| " + 
                   String.format(padding, fields[i][fields[i].length - 1]) +
                  " |\n";
        }

        if (nomineeArr.length != 0)
        {
            System.out.println("\n" + sep);
            System.out.println(headerStr);
            System.out.println(sep);
            System.out.print(out);
            System.out.println(sep);
        }
    }

    public static int calcMaxStringArrLenght(String[] arr)
    {
        int length, maxLength;
        length = 0;
        maxLength = 0;

        for (String i : arr)
        {
            length = i.length();
            if (length > maxLength)
            {
                maxLength = length;
            }
        }

        return maxLength;
    }

    public static String formatPadding(String padding)
    {
        return String.format(padding, " ");
    }

    public static String formatPadding(String padding, char line)
    {
        return String.format(padding, " ").replace(' ', line);
    }

    public static void printHelp()
    {
        String msg[] = {
            "Usage: java Driver [House candidates file] [House preference file]",
            "",
            "Example house candidate file:",
            "    StateAb,DivisionID,DivisionNm,PartyAb,PartyNm," +
            "CandidateID,Surname,GivenNm,Elected,HistoricElected",
            "    NSW,151,Warringah,LP,Liberal,28624,ABBOTT,Tony,Y,Y",
            "",
            "Example house preference file:",
            "     StateAb,DivisionID,DivisionNm,PollingPlaceID,PollingPlace," +
            "CandidateID,Surname,GivenNm,BallotPosition,Elected," +
            "HistoricElected,PartyAb,PartyNm,OrdinaryVotes,Swing",
            "    WA,235,Brand,7472,Baldivis,28420,SCOTT,Philip,1,N,N,RUA," +
            "Rise Up Australia Party,176,4.28",
            ""
        };

        for (String inMsg : msg)
        {
            System.out.println(inMsg);
        }
    }
}
