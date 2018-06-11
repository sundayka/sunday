package mypig.lifecycle;

import java.util.EventObject;

public class LifecycleEvent extends EventObject {

    private Object data;
    private String type;
    private LifeCycle lifeCycle;

    public LifecycleEvent(String type,LifeCycle lifeCycle,Object data){
        super(null);
        this.type = type;
        this.lifeCycle = lifeCycle;
        this.data = data;
    }

    public LifecycleEvent(Object source) {
        super(source);
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LifeCycle getLifeCycle() {
        return lifeCycle;
    }

    public void setLifeCycle(LifeCycle lifeCycle) {
        this.lifeCycle = lifeCycle;
    }
}
