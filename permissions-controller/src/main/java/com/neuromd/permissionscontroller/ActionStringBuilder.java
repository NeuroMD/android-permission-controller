package com.neuromd.permissionscontroller;

public final class ActionStringBuilder {
    private final StringBuilder mActionStringBuilder;

    public ActionStringBuilder(){
        mActionStringBuilder = new StringBuilder();
    }

    public ActionStringBuilder(String initialString){
        mActionStringBuilder = new StringBuilder(initialString);
        mActionStringBuilder.append(";");
    }

    public ActionStringBuilder append(String string){
        mActionStringBuilder.append(string);
        mActionStringBuilder.append(";");
        return this;
    }

    @Override
    public String toString(){
        return mActionStringBuilder.toString();
    }
}
