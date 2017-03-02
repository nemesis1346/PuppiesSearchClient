package com.mywaytech.puppiessearchclient;

import android.os.Build;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by nemesis1346 on 2/3/2017.
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)
public class PostsSimulationTest {

//    addressResources = Arrays.asList("Hola, quisiera saber ¿Cómo lo puedo adoptar? ¿Debo llenar algún formulario o algo?", "Qué lindo, espero encuentre un hogar pronto.", "Su nombre es Toby busca un hogar donde le brinden mucho amor y mucho tiempo para ser feliz", "Cahorrita para adopción responsable", "Perrita en adopción se llama Sheyla se lleva muy bien con los niños", "Molly tiene dos años y quiere un hogar, le gustan mucho los niños", "Me llamo Richi busco un dueño responsable que me ame mucho tengo 4 años", "Mi perrita panda necesita un nuevo amigo para pasear alguien???", "Que buena aplicación mi perro por fin podrá conseguir nuevos amigos está muy solo", "Lucas mira hay muchos perritos que podemos conocer", "Tengo un gato y un perro alguien quiere conocerlos", "Este es Angelito y necesita un dueño responsable.", "Adoptame, soy Scooby doo soy muy juguetón y cariñoso, busco alguien que me quiera.", "Mi perrito está un poco decaído, alguien sabe que síntomas son los que tengo que ver para ver si está enfermo??");


    public static int daysBetween(Calendar from, Calendar to) {
        Calendar date = (Calendar) from.clone();
        int daysBetween = 0;
        while (date.before(to)) {
            date.add(Calendar.DAY_OF_MONTH, 1);
            daysBetween++;
        }
        System.out.println(daysBetween);
        return daysBetween;
    }

    public void randomTimeStamp(){
        Calendar now = Calendar.getInstance();
        Calendar min = Calendar.getInstance();
        Calendar randomDate = (Calendar) now.clone();
        int minYear = 2012;
        int minMonth = 2;
        int minDay = 18;
        min.set(minYear, minMonth-1, minDay);
        int numberOfDaysToAdd = (int) (Math.random() * (daysBetween(min, now) + 1));
        randomDate.add(Calendar.DAY_OF_YEAR, -numberOfDaysToAdd);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        System.out.println(dateFormat.format(now.getTime()));
        System.out.println(dateFormat.format(min.getTime()));
        System.out.println(dateFormat.format(randomDate.getTime()));
    }
}
