package com.mywaytech.puppiessearchclient;

import android.os.Build;
import android.util.Log;

import com.google.gson.Gson;
import com.mywaytech.puppiessearchclient.models.NewUserModel;
import com.mywaytech.puppiessearchclient.models.ReportModel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Created by nemesis1346 on 2/3/2017.
 */
@Config(constants = BuildConfig.class, sdk = Build.VERSION_CODES.LOLLIPOP)
@RunWith(RobolectricGradleTestRunner.class)

public class SimulationTest {

    private List<NewUserModel> userModels;
    private List<ReportModel> reportModels;

    private NewUserModel currentUserModel;
    private ReportModel currentReportModel;
    private List<String> namesResources;
    private List<String> lastNameResources;
    private List<Integer> passwordResources;
    private List<String> commentsResources;
    private List<String> emailResources;
    private List<String> addressResources;
    private List<String> typeResources;
    private List<String> namesFemaleResources;
    private List<String> emailFemaleResources;


    @Test
    public void simulateUsers() throws Exception {
        ShadowLog.setupLogging();
        ShadowLog.stream = System.out;
        //FIRST TEST

        namesResources = Arrays.asList("Marco", "Antonio", "Daniel", "Andres", "Ignacio", "Ronald", "Jose", "William", "Miguel", "Mauricio", "Ruben", "Edison", "Leonardo", "Luis", "Cristian", "Fernando", "Vagner", "Danielo", "Daniel", "Oswaldo", "Pedro", "Diego", "Zack", "Inti", "Huascar");
        lastNameResources = Arrays.asList("Maigua", "Teran", "Molina", "Zambrano", "Altamirano", "Cartagenova", "Lema", "Quimbo", "Cabascango", "Meneses", "Miranda", "Aguilar", "Cachiguango", "Jackson", "Males", "Sanchez", "Chango", "Mosquera", "Amay", "Mendoza");
        passwordResources = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        emailResources = Arrays.asList("gmail", "hotmail", "yahoo", "outlook");
        addressResources = Arrays.asList("Av. América", "Av. Amazonas", "Juan Leon Mera", "Jeronimo Carrion", "Bolivar", "Morales", "Quito", "Colon", "Av. 6 de diciembre", "12 de Octubre", "Vicentina", "9 de Octubre", "Av. Naciones Unidas", "Plaza Artigas");
        commentsResources = Arrays.asList("Hola, quisiera saber ¿Cómo lo puedo adoptar? ¿Debo llenar algún formulario o algo?", "Qué lindo, espero encuentre un hogar pronto.", "Su nombre es Toby busca un hogar donde le brinden mucho amor y mucho tiempo para ser feliz", "Cahorrita para adopción responsable", "Perrita en adopción se llama Sheyla se lleva muy bien con los niños", "Molly tiene dos años y quiere un hogar, le gustan mucho los niños", "Me llamo Richi busco un dueño responsable que me ame mucho tengo 4 años", "Mi perrita panda necesita un nuevo amigo para pasear alguien???", "Que buena aplicación mi perro por fin podrá conseguir nuevos amigos está muy solo", "Lucas mira hay muchos perritos que podemos conocer", "Tengo un gato y un perro alguien quiere conocerlos", "Este es Angelito y necesita un dueño responsable.", "Adoptame, soy Scooby doo soy muy juguetón y cariñoso, busco alguien que me quiera.", "Mi perrito está un poco decaído, alguien sabe que síntomas son los que tengo que ver para ver si está enfermo??");
        typeResources = Arrays.asList("ADOPTION", "LOST");
        namesFemaleResources=Arrays.asList("Alexandra","Mayra","Daniela","Jessica","Jazmin","Rosa","Maria","Luz","Yesenia","Taki","Wary","Soledad","Debora","Jhoana","Andrea","Karen","Elcy","Blanca","Violeta","Paola","Paulina","Esther","Sami","Samanta","Anita","Carolina");
        createLists();
        parseJsons();
    }

    private void createLists() throws ParseException {
        int sizeSampleMaleUsers =50;
        int sizeSampleFemaleUsers=100;

        userModels = new ArrayList<>();
        reportModels = new ArrayList<>();

        Random r = new Random();
        int counterRandom1;
        int counterRandom2;
        int counterRandom3;
        int counterRandom4;
        int passCounter1;
        int passCounter2;
        int passCounter3;
        int passCounter4;
        int passCounter5;
        int passCounter6;
        int emailCounter;
        int addressCounter1;
        int addressCounter2;
        int commentCounter;
        int typeCounter;
        int imageCounter;

        //JUST FOR MALE USERS
        for (int i = 0; i < sizeSampleMaleUsers; i++) {
            //JUST USERS
            counterRandom1 = r.nextInt(namesResources.size());
            counterRandom2 = r.nextInt(namesResources.size());
            counterRandom3 = r.nextInt(lastNameResources.size());
            counterRandom4 = r.nextInt(lastNameResources.size());

            passCounter1 = r.nextInt(passwordResources.size());
            passCounter2 = r.nextInt(passwordResources.size());
            passCounter3 = r.nextInt(passwordResources.size());
            passCounter4 = r.nextInt(passwordResources.size());
            passCounter5 = r.nextInt(passwordResources.size());
            passCounter6 = r.nextInt(passwordResources.size());

            emailCounter = r.nextInt(emailResources.size());

            addressCounter1 = r.nextInt(addressResources.size());
            addressCounter2 = r.nextInt(addressResources.size());

            commentCounter = r.nextInt(commentsResources.size());
            typeCounter=r.nextInt(typeResources.size());

            currentUserModel = new NewUserModel();
            currentUserModel.setmName(namesResources.get(counterRandom1) + " " + namesResources.get(counterRandom2) + " " + lastNameResources.get(counterRandom3) + " " + lastNameResources.get(counterRandom4));
            currentUserModel.setmPassword(String.valueOf(passwordResources.get(passCounter1)) + String.valueOf(passwordResources.get(passCounter2)) + String.valueOf(passwordResources.get(passCounter3)) + String.valueOf(passwordResources.get(passCounter4)) + String.valueOf(passwordResources.get(passCounter5)) + String.valueOf(passwordResources.get(passCounter6)));
            currentUserModel.setmEmail(namesResources.get(counterRandom1) + "." + lastNameResources.get(counterRandom3) + "@" + emailResources.get(emailCounter) + ".com");
            currentUserModel.setAddress(addressResources.get(addressCounter1) + " y " + addressResources.get(addressCounter2));
            currentUserModel.setUid("TEST_" + UUID.randomUUID().toString());
            currentUserModel.setUserImagePath("userPicture/testImage_" + i + ".jpg");

            userModels.add(currentUserModel);

            //JUST POSTS
            currentReportModel = new ReportModel();
            currentReportModel.setuUserId(currentUserModel.getmUid());
            currentReportModel.setuId("TEST_POST_" + UUID.randomUUID().toString());
            currentReportModel.setImagePath("images/petImageTEST_" + i + ".jpg");
            currentReportModel.setuAddress(addressResources.get(addressCounter1) + " y " + addressResources.get(addressCounter2));
            currentReportModel.setuComment(commentsResources.get(commentCounter));
            currentReportModel.setuDate("-" + randomTimeStamp());
            currentReportModel.setuEmail(namesResources.get(counterRandom1) + "." + lastNameResources.get(counterRandom3) + "@" + emailResources.get(emailCounter) + ".com");
            currentReportModel.setuName(currentUserModel.getmEmail());
            currentReportModel.setuType(typeResources.get(typeCounter));
            reportModels.add(currentReportModel);
        }

        //JUST FOR FEMALE USERS
        for (int i = sizeSampleMaleUsers; i < sizeSampleFemaleUsers; i++) {
            //JUST USERS
            counterRandom1 = r.nextInt(namesFemaleResources.size());
            counterRandom2 = r.nextInt(namesFemaleResources.size());
            counterRandom3 = r.nextInt(lastNameResources.size());
            counterRandom4 = r.nextInt(lastNameResources.size());

            passCounter1 = r.nextInt(passwordResources.size());
            passCounter2 = r.nextInt(passwordResources.size());
            passCounter3 = r.nextInt(passwordResources.size());
            passCounter4 = r.nextInt(passwordResources.size());
            passCounter5 = r.nextInt(passwordResources.size());
            passCounter6 = r.nextInt(passwordResources.size());

            emailCounter = r.nextInt(emailResources.size());

            addressCounter1 = r.nextInt(addressResources.size());
            addressCounter2 = r.nextInt(addressResources.size());

            commentCounter = r.nextInt(commentsResources.size());
            typeCounter=r.nextInt(typeResources.size());

            currentUserModel = new NewUserModel();
            currentUserModel.setmName(namesFemaleResources.get(counterRandom1) + " " + namesFemaleResources.get(counterRandom2) + " " + lastNameResources.get(counterRandom3) + " " + lastNameResources.get(counterRandom4));
            currentUserModel.setmPassword(String.valueOf(passwordResources.get(passCounter1)) + String.valueOf(passwordResources.get(passCounter2)) + String.valueOf(passwordResources.get(passCounter3)) + String.valueOf(passwordResources.get(passCounter4)) + String.valueOf(passwordResources.get(passCounter5)) + String.valueOf(passwordResources.get(passCounter6)));
            currentUserModel.setmEmail(namesFemaleResources.get(counterRandom1) + "." + lastNameResources.get(counterRandom3) + "@" + emailResources.get(emailCounter) + ".com");
            currentUserModel.setAddress(addressResources.get(addressCounter1) + " y " + addressResources.get(addressCounter2));
            currentUserModel.setUid("TEST_" + UUID.randomUUID().toString());
            currentUserModel.setUserImagePath("userPicture/testImage_" + i + ".jpg");

            userModels.add(currentUserModel);

            //JUST POSTS
            currentReportModel = new ReportModel();
            currentReportModel.setuUserId(currentUserModel.getmUid());
            currentReportModel.setuId("TEST_POST_" + UUID.randomUUID().toString());
            currentReportModel.setImagePath("images/petImageTEST_" + i + ".jpg");
            currentReportModel.setuAddress(addressResources.get(addressCounter1) + " y " + addressResources.get(addressCounter2));
            currentReportModel.setuComment(commentsResources.get(commentCounter));
            currentReportModel.setuDate("-" + randomTimeStamp());
            currentReportModel.setuEmail(namesFemaleResources.get(counterRandom1) + "." + lastNameResources.get(counterRandom3) + "@" + emailResources.get(emailCounter) + ".com");
            currentReportModel.setuName(currentUserModel.getmEmail());
            currentReportModel.setuType(typeResources.get(typeCounter));
            reportModels.add(currentReportModel);
        }
    }

    private void parseJsons() {
        String finalResultUsers = "{";
        for (int i = 0; i < userModels.size(); i++) {
            Gson gson = new Gson();
            NewUserModel currentUserModel = userModels.get(i);
            String jsonInString = gson.toJson(currentUserModel);
            String result = "\"" + currentUserModel.getmUid() + "\"" + ":" + jsonInString;
            if (i < userModels.size() - 1) {
                finalResultUsers += result + ",";
            } else {
                finalResultUsers += result;
            }
        }
        finalResultUsers += "}";
        Log.i("finalJsonUsers: ", "" + finalResultUsers);

        String finalResultReports= "{";
        for (int i = 0; i < reportModels.size(); i++) {
            Gson gson = new Gson();
            ReportModel currentReportModel = reportModels.get(i);
            String jsonInString=gson.toJson(currentReportModel);
            String result = "\"" + currentReportModel.getuId() + "\"" + ":" + jsonInString;
            if(i<reportModels.size()-1){
                finalResultReports+=result+",";
            }else{
                finalResultReports+=result;
            }
        }
        finalResultReports+="}";
        Log.i("finalJsonReports: ", "" + finalResultReports);

    }


    public static String convertStringDateToTimestamp(String input) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date netDate = sdf.parse(input);
        long timestampResult = netDate.getTime() / 1000L;
        return String.valueOf(timestampResult);
    }

    public String randomTimeStamp() throws ParseException {
        ShadowLog.setupLogging();
        ShadowLog.stream = System.out;
        //FIRST TEST
        Calendar now = Calendar.getInstance();
        Calendar min = Calendar.getInstance();
        Calendar randomDate = (Calendar) now.clone();
        int minYear = 2016;
        int minMonth = 10;
        int minDay = 18;
        min.set(minYear, minMonth - 1, minDay);
        int numberOfDaysToAdd = (int) (Math.random() * (daysBetween(min, now) + 1));
        randomDate.add(Calendar.DAY_OF_YEAR, -numberOfDaysToAdd);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Log.i("random time:", dateFormat.format(randomDate.getTime()));
        Log.i("random time timestamp:", convertStringDateToTimestamp(dateFormat.format(randomDate.getTime())));
        return convertStringDateToTimestamp(dateFormat.format(randomDate.getTime()));
    }

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
}
