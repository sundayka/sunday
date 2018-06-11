package mypig.lifecycle;

import java.util.Arrays;

public class LifecycleSupport {

    private LifecycleListener[] listeners = new LifecycleListener[0];
    private LifeCycle lifeCycle;

    public LifecycleSupport(LifeCycle lifeCycle){
        this.lifeCycle = lifeCycle;
    }

    public void fireLifecycleEvent(String type,Object data){
        LifecycleEvent event = new LifecycleEvent(type,this.lifeCycle,data);
        LifecycleListener[] interested = null;
        synchronized (listeners){
            interested = listeners;
            for (LifecycleListener listener :interested){
                listener.lifecycleEvent(event);
            }
        }
    }

    public void addListener(LifecycleListener listener){
        LifecycleListener[] result = new LifecycleListener[listeners.length+1];
        for (int i=0;i<listeners.length;i++){
            result[i] = listeners[i];
        }
        result[listeners.length] = listener;
    }

    public void removeListener(LifecycleListener listener){
        boolean condition = false;
        LifecycleListener[] result = new LifecycleListener[listeners.length];
        int j = 0;
        for (int i = 0;i<listeners.length;i++){
            if (listener != listeners[i]){
                result[j++] = listeners[i];
            }else {
                condition = true;
            }
        }
        listeners = Arrays.copyOf(result,j);
        if (!condition){
            System.out.println("未发现此listener");
        }
    }

    public LifecycleListener[] findListeners(){
        return listeners;
    }

}
