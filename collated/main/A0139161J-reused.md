# A0139161J-reused
###### \java\seedu\task\logic\parser\ParserUtil.java
``` java
    /* Returns String in format of hh:mm:ss
     * Precond: dateTime string formed by NattyParser required as input
     */
    private static String getTime(String dateTime) {
        List<String> output = new ArrayList<String>();
        StringTokenizer st = new StringTokenizer(dateTime);
        List<String> list = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            list.add(st.nextToken());
        }
        st = new StringTokenizer(list.get(3), ":");
        while (st.hasMoreTokens()) {
            output.add(st.nextToken());
        }
        return new String(output.get(0) + ":" + output.get(1));
    }

    private static String nattyParser (Optional<String> deadline,
            String fromDate, String fromTime, String toDate, String toTime) {
        Parser parser = new Parser();
        String deadlineString = deadline.get();
        List <DateGroup> groups = parser.parse(deadlineString);
        List dates = null;
        int line;
        int column;
        String matchingValue;
        String syntaxTree;
        Map parseMap;
        boolean isRecurring;
        Date recursUntil;

        for (DateGroup group: groups) {
            dates = group.getDates();
            line = group.getLine();
            column = group.getPosition();
            matchingValue = group.getText();
            syntaxTree = group.getSyntaxTree().toStringTree();
            parseMap = group.getParseLocations();
            isRecurring = group.isRecurring();
            recursUntil = group.getRecursUntil();
        }

        if (dates != null) {
            fromDate = dates.get(0).toString();
            fromTime = getTime(fromDate);
            if (dates.size() != 1) {
                toDate = dates.get(1).toString();
                toTime = getTime(toDate);
                isEvent = true;
            }
        }
        StringTokenizer st = new StringTokenizer(fromDate);
        List<String> listDeadline = new ArrayList<String>();
        while (st.hasMoreTokens()) {
            listDeadline.add(st.nextToken());
        }
        List<String> endOfEvent = new ArrayList<String>();
        if (isEvent) {
            st = new StringTokenizer(toDate);
            while (st.hasMoreTokens()) {
                endOfEvent.add(st.nextToken());
            }
        }
        StringBuilder deadlineStringBuilder = new StringBuilder();
        deadlineStringBuilder.append(listDeadline.get(2) + "-" + listDeadline.get(1)
            + "-" + listDeadline.get(5) + " @ " + fromTime);
        if (isEvent) {
            deadlineStringBuilder.append(" to " + endOfEvent.get(2) + "-" + endOfEvent.get(1)
                + "-" + endOfEvent.get(5) + " @ " + toTime);
        }
        return deadlineStringBuilder.toString();
    }
}
```
