package screensaver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

@org.springframework.stereotype.Component
//В итоге создаться фррэйм в разных местах, но цвет обновляться не будет.
//Frame-singletone
//Color-prototype
//Необходимо обновить prototype в singletone
//!!!!!!!- очень плохое решение проинжектить ApplicationContext в ColorFrame, чтобы вытащить bean цвет.
// Теперь из-за какого-то цвета ColorFrame связан с контекстом- так делать нельзя!!!!!!!!!!!!!!!!
public abstract class ColorFrame extends JFrame {
    //вместо инжекта color прокидываем метод getColot();
    //@Autowired
    //private Color color;


    public ColorFrame(){
        setSize(200,200);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
    public void showOnRandomPlace(){
        Random random=new Random();
        setLocation(random.nextInt(1200),random.nextInt(700));
        getContentPane().setBackground(getColor());//Передаём сюда абстрактный метод getColor(). Он будет переопределён при формировании бина
        repaint();

    }

    protected abstract Color getColor();//Добавился абстрактный метод и следовательно класс стал абстрактным.
                                        //А там где будет прописан бин ColorFrame будет реализации метода getColor
                                        //который вернёт
}
