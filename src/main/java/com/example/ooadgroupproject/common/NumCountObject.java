package com.example.ooadgroupproject.common;

import lombok.Getter;
import org.apache.poi.ss.formula.functions.T;

import java.util.Comparator;

public class NumCountObject implements Comparable<NumCountObject> {
    public final Object content;
    public int num;
    public NumCountObject(Object content, int num) {
        this.content = content;
        this.num = num;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof NumCountObject){
            return this.num==((NumCountObject)obj).num;
        }else {
            return false;
        }
    }
    @Override
    public int compareTo(NumCountObject o) {
        return Long.compare(o.num, this.num);
    }

}
