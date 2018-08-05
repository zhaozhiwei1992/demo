package com.lx.demo.javabeans;

import java.beans.PropertyEditorSupport;

/**
 * id屬性設置器
 * 參考 com.sun.beans.editors.BooleanEditor
 */
public class IDPropertyEditor extends PropertyEditorSupport {
    public IDPropertyEditor() {
    }

    public void setAsText(String var1) throws IllegalArgumentException {
        if (var1 == null) {
            this.setValue(Long.MIN_VALUE);
        } else {
            /*
            *  在父類實現中需要觸發事件監聽
            *  public void setValue(Object value) {
                this.value = value;
                firePropertyChange();
              }*/
            this.setValue(Long.parseLong(var1));
        }

    }
}
