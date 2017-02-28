package com.thinkmobiles.android.samples.domain;

public abstract class ObjectGraph implements IObjectGraph {

    /*protected*/ static IObjectGraph graph;

    public static IObjectGraph getInstance() {
        return graph;
    }

}
