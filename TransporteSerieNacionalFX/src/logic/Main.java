package logic;

import java.io.IOException;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {


        Controller probando = Controller.getSingletonController();
        probando.setPosChampion(-1);
        probando.setPosSubChampion(-1);
        probando.setSecondRound(false);
        ArrayList<Integer> indexes = new ArrayList<>();

        indexes.add(0);
        indexes.add(1);
        indexes.add(2);
        indexes.add(3);
        indexes.add(4);
        indexes.add(5);
        indexes.add(6);
        indexes.add(7);
        indexes.add(8);
        indexes.add(9);
        indexes.add(10);
        indexes.add(11);
        indexes.add(12);
        indexes.add(13);
        indexes.add(14);
        indexes.add(15);

        probando.setIndexes(indexes);

        probando.generateCalendar();



        //probando.changeDuel(probando.getCalendar());

		/*System.out.println("Calendario Final:");
		for (int i= 0; i < calendar.size(); i++) {
			for(int j=0; j < calendar.get(i).getGames().size();j++){
				int posLocal = calendar.get(i).getGames().get(j).get(0);
				int posVisitor = calendar.get(i).getGames().get(j).get(1);
				System.out.print("["+probando.getTeams().get(posLocal)+","+probando.getTeams().get(posVisitor)+"]");

			}
			System.out.println(" ");
		}*/
		/*System.out.println("---------------");
		System.out.println("Distancia:");
		System.out.println(probando.calculateDistance(calendar));
		System.out.println("---------------");
		System.out.println("Cambiar posición de una fecha ");
		probando.changeDatePosition(calendar);
		System.out.println("---------------");
		System.out.println("Intercambiar dos equipos");
		probando.changeTeams(calendar);
		System.out.println("---------------");
		System.out.println("Intercambiar locales y visitantes en una fecha");
		probando.changeLocalAndVisitorOnADate(calendar);*/


    }

}
