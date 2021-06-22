
package com.example.springbootjcrontab.jcrontab.log;

import java.io.PrintWriter;
import java.util.Vector;

public class VectorWriter extends PrintWriter {

    VectorWriter() {
        super(new NullWriter());
        v = new Vector();
    }

    public void print(Object o) {
        v.addElement(o.toString());
    }

    public void print(String s) {
        v.addElement(s);
    }

    public void print(char chars[]) {
        v.addElement(new String(chars));
    }

    public void println(Object o) {
        v.addElement(o.toString());
    }

    public void println(String s) {
        v.addElement(s);
    }

    public void println(char chars[]) {
        v.addElement(new String(chars));
    }

    public String[] toStringArray() {
        int len = v.size();
        String sa[] = new String[len];
        for (int i = 0; i < len; i++) {
            sa[i] = (String) v.elementAt(i);
        }

        return sa;
    }

    public String toStrings() {
        StringBuffer sb = new StringBuffer();

        int len = v.size();
        for (int i = 0; i < len; i++) {
            sb.append((String) v.elementAt(i)).append("\r");
        }

        return sb.toString();
    }

    public void write(String s) {
        v.addElement(s);
    }

    public void write(String s, int off, int len) {
        v.addElement(s.substring(off, off + len));
    }

    public void write(char chars[]) {
        v.addElement(new String(chars));
    }

    public void write(char chars[], int off, int len) {
        v.addElement(new String(chars, off, len));
    }

    private Vector v;
}