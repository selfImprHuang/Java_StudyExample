package pattern;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.EventObject;
import java.util.List;

/**
 * @author Administrator
 */
public class ObserverController {

    public static void main(String[] args) throws InterruptedException{
        //加到集合中，使垃圾无法回收
        List<EmptyObject> emptys = new ArrayList<>();
        for(int i=0;i<100;i++){
            emptys.add(new EmptyObject());
        }
        //打开jvisualvm,查看EmptyObject的大小为16字节
        Thread.sleep(60*1000);
    }
    private static class EmptyObject{}

    private static int get() {
        try{
            System.out.println("try");
        }finally {
            int i = 1/0;
            System.out.println("catch");
            return 1;
        }
    }

}

/**
 * 事件，一般是继承了EventObject
 * 事件就是作为事件监听器触发的依据
 */
class DoorEvent extends EventObject {

    /**
     * Constructs a prototypical Event.
     *
     * @param    source    The object on which the Event initially occurred.
     * @exception IllegalArgumentException  if source is null.
     */
    public DoorEvent(Object source,String control) {
        super(source);
        this.control = control;
    }

    private String control;

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public void open() {
        System.out.println("open the door");
    }

    public void close() {
        System.out.println("close the door");
    }
}

/**
 * 事件监听器，获取到对应的事件，进行事件监听，根据不同的事件作出不同的反馈
 * 一般一类事件监听器对应一类事件
 */
interface DoorEventListener extends EventListener {
    void doorControl(DoorEvent doorEvent);
}


class DoorListenerImpl implements DoorEventListener {

    @Override
    public void doorControl(DoorEvent doorEvent) {
        if ("open".equals(doorEvent.getControl())) {
            doorEvent.open();
        } else if ("close".equals(doorEvent.getControl())) {
            doorEvent.close();
        }
    }
}

/**
 * 事件源，可以理解为事件发生的地方
 * 他有一组时间监听器，当发生一个对应的事情的时候，通知对应的事件监听器去处理对应的事件
 */
class DoorEventSource {
    private List<DoorEventListener> doorEventListener;

    public List<DoorEventListener> getDoorEventListener() {
        return doorEventListener;
    }

    public void setDoorEventListener(List<DoorEventListener> doorEventListener) {
        this.doorEventListener = doorEventListener;
    }

    public void openDoor(){
        System.out.println("我要进门了");
        doorEventListener.forEach(item->item.doorControl(new DoorEvent(this,"open")));
    }



    public void closeDoor(){
        System.out.println("我要出门了");
        doorEventListener.forEach(item->item.doorControl(new DoorEvent(this,"close")));
    }
}
