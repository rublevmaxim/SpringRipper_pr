package quoters;

//Чтобы прописатся в MBean server, который стартует вместе с Java, необхлдимо
//создать и имплементировать интерфейс с таким же названием ка класс, а на конце MBean.
//В Mbean необходимо указать те методы, которые хотим вызыват JXM Console
public class ProfilingController implements ProfilingControllerMBean{
    private boolean enabled=true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
