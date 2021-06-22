
package com.example.springbootjcrontab.jcrontab.data;

import org.jcrontab.data.CrontabEntryBean;
import org.jcrontab.data.CrontabEntryException;

import java.util.StringTokenizer;

public class CrontabParser {
    /**
     * @Description:.
     * @param entry ����
     * @return .
     * @throws CrontabEntryException �쳣
     * @throws
     */
    public CrontabEntryBean marshall(String entry) throws CrontabEntryException {
        boolean[] bHours = new boolean[24];
        boolean[] bMinutes = new boolean[60];
        boolean[] bMonths = new boolean[12];
        boolean[] bDaysOfWeek = new boolean[7];
        boolean[] bDaysOfMonth = new boolean[31];
        CrontabEntryBean ceb = new CrontabEntryBean();
        StringTokenizer tokenizer = new StringTokenizer(entry);
        int numTokens = tokenizer.countTokens();
        for (int i = 0; tokenizer.hasMoreElements(); i++) {
            String token = tokenizer.nextToken();
            switch (i) {
            case 1: // Minutes
                parseToken(token, bMinutes, false);
                ceb.setBMinutes(bMinutes);
                ceb.setMinutes(token);
                break;
            case 2: // Hours
                parseToken(token, bHours, false);
                ceb.setBHours(bHours);
                ceb.setHours(token);
                break;
            case 3: // Days of month
                parseToken(token, bDaysOfMonth, true);
                ceb.setBDaysOfMonth(bDaysOfMonth);
                ceb.setDaysOfMonth(token);
                break;
            case 4: // Months
                parseToken(token, bMonths, true);
                ceb.setBMonths(bMonths);
                ceb.setMonths(token);
                break;
            case 5: // Days of week
                parseToken(token, bDaysOfWeek, false);
                ceb.setBDaysOfWeek(bDaysOfWeek);
                ceb.setDaysOfWeek(token);
                break;
            case 7: // Name of the class
                String className,
                methodName;
                try {
                    int index = token.indexOf("#");
                    if (index > 0) {
                        StringTokenizer tokenize = new StringTokenizer(token, "#");
                        className = tokenize.nextToken();
                        methodName = tokenize.nextToken();
                        ceb.setClassName(className);
                        ceb.setMethodName(methodName);
                    } else {
                        className = token;
                        ceb.setClassName(className);
                    }
                } catch (Exception e) {
                    throw new CrontabEntryException(entry);
                }
                break;
            case 8: // Extra Information
                String[] extraInfo = new String[numTokens - 8];
                boolean bextraInfo = true;
                for (extraInfo[i - 8] = token; tokenizer.hasMoreElements(); extraInfo[i - 8] = tokenizer.nextToken()) {
                    i++;
                }
                ceb.setBExtraInfo(bextraInfo);
                ceb.setExtraInfo(extraInfo);
                break;
            default:
                break;
            }
        }
        // At least 6 token
        if (numTokens < 8) {
            throw new CrontabEntryException("The number of items is < 8 at " + entry);
        }
        return ceb;
    }

    /**
     * @Description:.
     * @param ceb .
     * @return .
     * @throws CrontabEntryException .
     * @throws
     */
    public String unmarshall(CrontabEntryBean ceb) throws CrontabEntryException {
        final StringBuffer sb = new StringBuffer();
        sb.append(ceb.getMinutes() + " ");
        sb.append(ceb.getHours() + " ");
        sb.append(ceb.getDaysOfMonth() + " ");
        sb.append(ceb.getMonths() + " ");
        sb.append(ceb.getDaysOfWeek() + " ");
        if ("".equals(ceb.getMethodName())) {
            sb.append(ceb.getClassName() + " ");
        } else {
            sb.append(ceb.getClassName() + "#" + ceb.getMethodName() + " ");
        }
        String[] extraInfo = ceb.getExtraInfo();
        if (extraInfo != null) {
            for (int i = 0; i < extraInfo.length; i++) {
                sb.append(extraInfo[i] + " ");
            }
        }
        return sb.toString();

    }

    /**
     * @Description:.
     * @param token .
     * @param arrayBool .
     * @param bBeginInOne .
     * @throws CrontabEntryException .
     * @throws
     */
    public void parseToken(String token, boolean[] arrayBool, boolean bBeginInOne) throws CrontabEntryException {
        // This line initializes all the array of booleans instead of doing so
        // in the CrontabEntryBean Constructor.
        // for (int i = 0; i < arrayBool.length ; i++) arrayBool[i]=false;
        int i;
        int index;
        int each = 1;
        try {
            // Look for step first
            index = token.indexOf("/");
            if (index > 0) {
                each = Integer.parseInt(token.substring(index + 1));
                token = token.substring(0, index);
            }

            if (token.equals("*")) {
                for (i = 0; i < arrayBool.length; i += each) {
                    arrayBool[i] = true;
                }
                return;
            }

            index = token.indexOf(",");
            if (index > 0) {
                StringTokenizer tokenizer = new StringTokenizer(token, ",");
                while (tokenizer.hasMoreTokens()) {
                    parseToken(tokenizer.nextToken(), arrayBool, bBeginInOne);
                }
                return;
            }

            index = token.indexOf("-");
            if (index > 0) {
                int start = Integer.parseInt(token.substring(0, index));
                int end = Integer.parseInt(token.substring(index + 1));

                if (bBeginInOne) {
                    start--;
                    end--;
                }
                for (int j = start; j <= end; j += each) {
                    arrayBool[j] = true;
                }
                return;
            }

            int iValue = Integer.parseInt(token);
            if (bBeginInOne) {
                iValue--;
            }
            arrayBool[iValue] = true;
            return;
        } catch (Exception e) {
            throw new CrontabEntryException("It was wrong with " + token);
        }
    }
}