package logic;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Main_Rosete_Copia {

	public static void main(String[] args) {
		/*
		 Controller probando = Controller.getSingletonController();
	      
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

	       
	        ArrayList<Integer> e1f1 = new ArrayList<>();
	        e1f1.add(15);
	        e1f1.add(14);
	        ArrayList<Integer> e2f1 = new ArrayList<>();
	        e2f1.add(0);
	        e2f1.add(3);
	        ArrayList<Integer> e3f1 = new ArrayList<>();
	        e3f1.add(4);
	        e3f1.add(9);
	        ArrayList<Integer> e4f1 = new ArrayList<>();
	        e4f1.add(6);
	        e4f1.add(2);
	        ArrayList<Integer> e5f1 = new ArrayList<>();
	        e5f1.add(8);
	        e5f1.add(1);
	        ArrayList<Integer> e6f1 = new ArrayList<>();
	        e6f1.add(10);
	        e6f1.add(7);
	        ArrayList<Integer> e7f1 = new ArrayList<>();
	        e7f1.add(12);
	        e7f1.add(13);
	        ArrayList<Integer> e8f1 = new ArrayList<>();
	        e8f1.add(11);
	        e8f1.add(5);
	        ArrayList<ArrayList<Integer>> gf1 = new ArrayList<>();
	        gf1.add(e1f1);
	        gf1.add(e2f1);
	        gf1.add(e3f1);
	        gf1.add(e4f1);
	        gf1.add(e5f1);
	        gf1.add(e6f1);
	        gf1.add(e7f1);
	        gf1.add(e8f1);
	        Date f1 = new Date(gf1);


	        ArrayList<Integer> e1f2 = new ArrayList<>();
	        e1f2.add(2);
	        e1f2.add(11);
	        ArrayList<Integer> e2f2 = new ArrayList<>();
	        e2f2.add(0);
	        e2f2.add(1);
	        ArrayList<Integer> e3f2 = new ArrayList<>();
	        e3f2.add(7);
	        e3f2.add(5);
	        ArrayList<Integer> e4f2 = new ArrayList<>();
	        e4f2.add(8);
	        e4f2.add(6);
	        ArrayList<Integer> e5f2 = new ArrayList<>();
	        e5f2.add(10);
	        e5f2.add(9);
	        ArrayList<Integer> e6f2 = new ArrayList<>();
	        e6f2.add(12);
	        e6f2.add(15);
	        ArrayList<Integer> e7f2 = new ArrayList<>();
	        e7f2.add(13);
	        e7f2.add(4);
	        ArrayList<Integer> e8f2 = new ArrayList<>();
	        e8f2.add(14);
	        e8f2.add(3);
	        ArrayList<ArrayList<Integer>> gf2 = new ArrayList<>();
	        gf2.add(e1f2);
	        gf2.add(e2f2);
	        gf2.add(e3f2);
	        gf2.add(e4f2);
	        gf2.add(e5f2);
	        gf2.add(e6f2);
	        gf2.add(e7f2);
	        gf2.add(e8f2);
	        Date f2 = new Date(gf2);


	        ArrayList<Integer> e1f3 = new ArrayList<>();
	        e1f3.add(1);
	        e1f3.add(11);
	        ArrayList<Integer> e2f3 = new ArrayList<>();
	        e2f3.add(2);
	        e2f3.add(8);
	        ArrayList<Integer> e3f3 = new ArrayList<>();
	        e3f3.add(15);
	        e3f3.add(7);
	        ArrayList<Integer> e4f3 = new ArrayList<>();
	        e4f3.add(3);
	        e4f3.add(13);
	        ArrayList<Integer> e5f3 = new ArrayList<>();
	        e5f3.add(4);
	        e5f3.add(12);
	        ArrayList<Integer> e6f3 = new ArrayList<>();
	        e6f3.add(6);
	        e6f3.add(14);
	        ArrayList<Integer> e7f3 = new ArrayList<>();
	        e7f3.add(5);
	        e7f3.add(10);
	        ArrayList<Integer> e8f3 = new ArrayList<>();
	        e8f3.add(9);
	        e8f3.add(0);
	        ArrayList<ArrayList<Integer>> gf3 = new ArrayList<>();
	        gf3.add(e1f3);
	        gf3.add(e2f3);
	        gf3.add(e3f3);
	        gf3.add(e4f3);
	        gf3.add(e5f3);
	        gf3.add(e6f3);
	        gf3.add(e7f3);
	        gf3.add(e8f3);
	        Date f3 = new Date(gf3);


	        ArrayList<Integer> e1f4 = new ArrayList<>();
	        e1f4.add(1);
	        e1f4.add(9);
	        ArrayList<Integer> e2f4 = new ArrayList<>();
	        e2f4.add(0);
	        e2f4.add(8);
	        ArrayList<Integer> e3f4 = new ArrayList<>();
	        e3f4.add(4);
	        e3f4.add(10);
	        ArrayList<Integer> e4f4 = new ArrayList<>();
	        e4f4.add(7);
	        e4f4.add(2);
	        ArrayList<Integer> e5f4 = new ArrayList<>();
	        e5f4.add(5);
	        e5f4.add(3);
	        ArrayList<Integer> e6f4 = new ArrayList<>();
	        e6f4.add(12);
	        e6f4.add(6);
	        ArrayList<Integer> e7f4 = new ArrayList<>();
	        e7f4.add(11);
	        e7f4.add(14);
	        ArrayList<Integer> e8f4 = new ArrayList<>();
	        e8f4.add(13);
	        e8f4.add(15);
	        ArrayList<ArrayList<Integer>> gf4 = new ArrayList<>();
	        gf4.add(e1f4);
	        gf4.add(e2f4);
	        gf4.add(e3f4);
	        gf4.add(e4f4);
	        gf4.add(e5f4);
	        gf4.add(e6f4);
	        gf4.add(e7f4);
	        gf4.add(e8f4);
	        Date f4 = new Date(gf4);


	        ArrayList<Integer> e1f5 = new ArrayList<>();
	        e1f5.add(2);
	        e1f5.add(5);
	        ArrayList<Integer> e2f5 = new ArrayList<>();
	        e2f5.add(3);
	        e2f5.add(12);
	        ArrayList<Integer> e3f5 = new ArrayList<>();
	        e3f5.add(8);
	        e3f5.add(15);
	        ArrayList<Integer> e4f5 = new ArrayList<>();
	        e4f5.add(9);
	        e4f5.add(7);
	        ArrayList<Integer> e5f5 = new ArrayList<>();
	        e5f5.add(10);
	        e5f5.add(0);
	        ArrayList<Integer> e6f5 = new ArrayList<>();
	        e6f5.add(11);
	        e6f5.add(4);
	        ArrayList<Integer> e7f5 = new ArrayList<>();
	        e7f5.add(13);
	        e7f5.add(6);
	        ArrayList<Integer> e8f5 = new ArrayList<>();
	        e8f5.add(14);
	        e8f5.add(1);
	        ArrayList<ArrayList<Integer>> gf5 = new ArrayList<>();
	        gf5.add(e1f5);
	        gf5.add(e2f5);
	        gf5.add(e3f5);
	        gf5.add(e4f5);
	        gf5.add(e5f5);
	        gf5.add(e6f5);
	        gf5.add(e7f5);
	        gf5.add(e8f5);
	        Date f5 = new Date(gf5);

	        ArrayList<Integer> e1f6 = new ArrayList<>();
	        e1f6.add(2);
	        e1f6.add(12);
	        ArrayList<Integer> e2f6 = new ArrayList<>();
	        e2f6.add(15);
	        e2f6.add(5);
	        ArrayList<Integer> e3f6 = new ArrayList<>();
	        e3f6.add(4);
	        e3f6.add(3);
	        ArrayList<Integer> e4f6 = new ArrayList<>();
	        e4f6.add(6);
	        e4f6.add(1);
	        ArrayList<Integer> e5f6 = new ArrayList<>();
	        e5f6.add(7);
	        e5f6.add(8);
	        ArrayList<Integer> e6f6 = new ArrayList<>();
	        e6f6.add(9);
	        e6f6.add(11);
	        ArrayList<Integer> e7f6 = new ArrayList<>();
	        e7f6.add(13);
	        e7f6.add(10);
	        ArrayList<Integer> e8f6 = new ArrayList<>();
	        e8f6.add(14);
	        e8f6.add(0);
	        ArrayList<ArrayList<Integer>> gf6 = new ArrayList<>();
	        gf6.add(e1f6);
	        gf6.add(e2f6);
	        gf6.add(e3f6);
	        gf6.add(e4f6);
	        gf6.add(e5f6);
	        gf6.add(e6f6);
	        gf6.add(e7f6);
	        gf6.add(e8f6);
	        Date f6 = new Date(gf6);

	        ArrayList<Integer> e1f7 = new ArrayList<>();
	        e1f7.add(1);
	        e1f7.add(4);
	        ArrayList<Integer> e2f7 = new ArrayList<>();
	        e2f7.add(3);
	        e2f7.add(8);
	        ArrayList<Integer> e3f7 = new ArrayList<>();
	        e3f7.add(0);
	        e3f7.add(7);
	        ArrayList<Integer> e4f7 = new ArrayList<>();
	        e4f7.add(6);
	        e4f7.add(15);
	        ArrayList<Integer> e5f7 = new ArrayList<>();
	        e5f7.add(5);
	        e5f7.add(9);
	        ArrayList<Integer> e6f7 = new ArrayList<>();
	        e6f7.add(10);
	        e6f7.add(2);
	        ArrayList<Integer> e7f7 = new ArrayList<>();
	        e7f7.add(12);
	        e7f7.add(11);
	        ArrayList<Integer> e8f7 = new ArrayList<>();
	        e8f7.add(13);
	        e8f7.add(14);
	        ArrayList<ArrayList<Integer>> gf7 = new ArrayList<>();
	        gf7.add(e1f7);
	        gf7.add(e2f7);
	        gf7.add(e3f7);
	        gf7.add(e4f7);
	        gf7.add(e5f7);
	        gf7.add(e6f7);
	        gf7.add(e7f7);
	        gf7.add(e8f7);
	        Date f7 = new Date(gf7);

	        ArrayList<Integer> e1f8 = new ArrayList<>();
	        e1f8.add(1);
	        e1f8.add(15);
	        ArrayList<Integer> e2f8 = new ArrayList<>();
	        e2f8.add(3);
	        e2f8.add(2);
	        ArrayList<Integer> e3f8 = new ArrayList<>();
	        e3f8.add(4);
	        e3f8.add(0);
	        ArrayList<Integer> e4f8 = new ArrayList<>();
	        e4f8.add(7);
	        e4f8.add(13);
	        ArrayList<Integer> e5f8 = new ArrayList<>();
	        e5f8.add(8);
	        e5f8.add(5);
	        ArrayList<Integer> e6f8 = new ArrayList<>();
	        e6f8.add(9);
	        e6f8.add(6);
	        ArrayList<Integer> e7f8 = new ArrayList<>();
	        e7f8.add(11);
	        e7f8.add(10);
	        ArrayList<Integer> e8f8 = new ArrayList<>();
	        e8f8.add(14);
	        e8f8.add(12);
	        ArrayList<ArrayList<Integer>> gf8 = new ArrayList<>();
	        gf8.add(e1f8);
	        gf8.add(e2f8);
	        gf8.add(e3f8);
	        gf8.add(e4f8);
	        gf8.add(e5f8);
	        gf8.add(e6f8);
	        gf8.add(e7f8);
	        gf8.add(e8f8);
	        Date f8 = new Date(gf8);

	        ArrayList<Integer> e1f9 = new ArrayList<>();
	        e1f9.add(2);
	        e1f9.add(1);
	        ArrayList<Integer> e2f9 = new ArrayList<>();
	        e2f9.add(15);
	        e2f9.add(11);
	        ArrayList<Integer> e3f9 = new ArrayList<>();
	        e3f9.add(0);
	        e3f9.add(13);
	        ArrayList<Integer> e4f9 = new ArrayList<>();
	        e4f9.add(4);
	        e4f9.add(7);
	        ArrayList<Integer> e5f9 = new ArrayList<>();
	        e5f9.add(6);
	        e5f9.add(3);
	        ArrayList<Integer> e6f9 = new ArrayList<>();
	        e6f9.add(5);
	        e6f9.add(14);
	        ArrayList<Integer> e7f9 = new ArrayList<>();
	        e7f9.add(10);
	        e7f9.add(8);
	        ArrayList<Integer> e8f9 = new ArrayList<>();
	        e8f9.add(12);
	        e8f9.add(9);
	        ArrayList<ArrayList<Integer>> gf9 = new ArrayList<>();
	        gf9.add(e1f9);
	        gf9.add(e2f9);
	        gf9.add(e3f9);
	        gf9.add(e4f9);
	        gf9.add(e5f9);
	        gf9.add(e6f9);
	        gf9.add(e7f9);
	        gf9.add(e8f9);
	        Date f9 = new Date(gf9);

	        ArrayList<Integer> e1f10 = new ArrayList<>();
	        e1f10.add(7);
	        e1f10.add(3);
	        ArrayList<Integer> e2f10 = new ArrayList<>();
	        e2f10.add(5);
	        e2f10.add(12);
	        ArrayList<Integer> e3f10 = new ArrayList<>();
	        e3f10.add(8);
	        e3f10.add(4);
	        ArrayList<Integer> e4f10 = new ArrayList<>();
	        e4f10.add(9);
	        e4f10.add(15);
	        ArrayList<Integer> e5f10 = new ArrayList<>();
	        e5f10.add(10);
	        e5f10.add(6);
	        ArrayList<Integer> e6f10 = new ArrayList<>();
	        e6f10.add(11);
	        e6f10.add(0);
	        ArrayList<Integer> e7f10 = new ArrayList<>();
	        e7f10.add(13);
	        e7f10.add(1);
	        ArrayList<Integer> e8f10 = new ArrayList<>();
	        e8f10.add(14);
	        e8f10.add(2);
	        ArrayList<ArrayList<Integer>> gf10 = new ArrayList<>();
	        gf10.add(e1f10);
	        gf10.add(e2f10);
	        gf10.add(e3f10);
	        gf10.add(e4f10);
	        gf10.add(e5f10);
	        gf10.add(e6f10);
	        gf10.add(e7f10);
	        gf10.add(e8f10);
	        Date f10 = new Date(gf10);

	        ArrayList<Integer> e1f11 = new ArrayList<>();
	        e1f11.add(1);
	        e1f11.add(10);
	        ArrayList<Integer> e2f11 = new ArrayList<>();
	        e2f11.add(2);
	        e2f11.add(4);
	        ArrayList<Integer> e3f11 = new ArrayList<>();
	        e3f11.add(3);
	        e3f11.add(15);
	        ArrayList<Integer> e4f11 = new ArrayList<>();
	        e4f11.add(6);
	        e4f11.add(5);
	        ArrayList<Integer> e5f11 = new ArrayList<>();
	        e5f11.add(7);
	        e5f11.add(11);
	        ArrayList<Integer> e6f11 = new ArrayList<>();
	        e6f11.add(8);
	        e6f11.add(13);
	        ArrayList<Integer> e7f11 = new ArrayList<>();
	        e7f11.add(9);
	        e7f11.add(14);
	        ArrayList<Integer> e8f11 = new ArrayList<>();
	        e8f11.add(12);
	        e8f11.add(0);
	        ArrayList<ArrayList<Integer>> gf11 = new ArrayList<>();
	        gf11.add(e1f11);
	        gf11.add(e2f11);
	        gf11.add(e3f11);
	        gf11.add(e4f11);
	        gf11.add(e5f11);
	        gf11.add(e6f11);
	        gf11.add(e7f11);
	        gf11.add(e8f11);
	        Date f11 = new Date(gf11);

	        ArrayList<Integer> e1f12 = new ArrayList<>();
	        e1f12.add(1);
	        e1f12.add(12);
	        ArrayList<Integer> e2f12 = new ArrayList<>();
	        e2f12.add(15);
	        e2f12.add(10);
	        ArrayList<Integer> e3f12 = new ArrayList<>();
	        e3f12.add(0);
	        e3f12.add(2);
	        ArrayList<Integer> e4f12 = new ArrayList<>();
	        e4f12.add(4);
	        e4f12.add(5);
	        ArrayList<Integer> e5f12 = new ArrayList<>();
	        e5f12.add(6);
	        e5f12.add(7);
	        ArrayList<Integer> e6f12 = new ArrayList<>();
	        e6f12.add(11);
	        e6f12.add(3);
	        ArrayList<Integer> e7f12 = new ArrayList<>();
	        e7f12.add(13);
	        e7f12.add(9);
	        ArrayList<Integer> e8f12 = new ArrayList<>();
	        e8f12.add(14);
	        e8f12.add(8);
	        ArrayList<ArrayList<Integer>> gf12 = new ArrayList<>();
	        gf12.add(e1f12);
	        gf12.add(e2f12);
	        gf12.add(e3f12);
	        gf12.add(e4f12);
	        gf12.add(e5f12);
	        gf12.add(e6f12);
	        gf12.add(e7f12);
	        gf12.add(e8f12);
	        Date f12 = new Date(gf12);

	        ArrayList<Integer> e1f13 = new ArrayList<>();
	        e1f13.add(2);
	        e1f13.add(15);
	        ArrayList<Integer> e2f13 = new ArrayList<>();
	        e2f13.add(0);
	        e2f13.add(6);
	        ArrayList<Integer> e3f13 = new ArrayList<>();
	        e3f13.add(7);
	        e3f13.add(12);
	        ArrayList<Integer> e4f13 = new ArrayList<>();
	        e4f13.add(5);
	        e4f13.add(1);
	        ArrayList<Integer> e5f13 = new ArrayList<>();
	        e5f13.add(9);
	        e5f13.add(8);
	        ArrayList<Integer> e6f13 = new ArrayList<>();
	        e6f13.add(10);
	        e6f13.add(3);
	        ArrayList<Integer> e7f13 = new ArrayList<>();
	        e7f13.add(11);
	        e7f13.add(13);
	        ArrayList<Integer> e8f13 = new ArrayList<>();
	        e8f13.add(14);
	        e8f13.add(4);
	        ArrayList<ArrayList<Integer>> gf13 = new ArrayList<>();
	        gf13.add(e1f13);
	        gf13.add(e2f13);
	        gf13.add(e3f13);
	        gf13.add(e4f13);
	        gf13.add(e5f13);
	        gf13.add(e6f13);
	        gf13.add(e7f13);
	        gf13.add(e8f13);
	        Date f13 = new Date(gf13);

	        ArrayList<Integer> e1f14 = new ArrayList<>();
	        e1f14.add(15);
	        e1f14.add(0);
	        ArrayList<Integer> e2f14 = new ArrayList<>();
	        e2f14.add(3);
	        e2f14.add(1);
	        ArrayList<Integer> e3f14 = new ArrayList<>();
	        e3f14.add(4);
	        e3f14.add(6);
	        ArrayList<Integer> e4f14 = new ArrayList<>();
	        e4f14.add(7);
	        e4f14.add(14);
	        ArrayList<Integer> e5f14 = new ArrayList<>();
	        e5f14.add(5);
	        e5f14.add(13);
	        ArrayList<Integer> e6f14 = new ArrayList<>();
	        e6f14.add(8);
	        e6f14.add(11);
	        ArrayList<Integer> e7f14 = new ArrayList<>();
	        e7f14.add(9);
	        e7f14.add(2);
	        ArrayList<Integer> e8f14 = new ArrayList<>();
	        e8f14.add(12);
	        e8f14.add(10);
	        ArrayList<ArrayList<Integer>> gf14 = new ArrayList<>();
	        gf14.add(e1f14);
	        gf14.add(e2f14);
	        gf14.add(e3f14);
	        gf14.add(e4f14);
	        gf14.add(e5f14);
	        gf14.add(e6f14);
	        gf14.add(e7f14);
	        gf14.add(e8f14);
	        Date f14 = new Date(gf14);

	        ArrayList<Integer> e1f15 = new ArrayList<>();
	        e1f15.add(1);
	        e1f15.add(7);
	        ArrayList<Integer> e2f15 = new ArrayList<>();
	        e2f15.add(15);
	        e2f15.add(4);
	        ArrayList<Integer> e3f15 = new ArrayList<>();
	        e3f15.add(3);
	        e3f15.add(9);
	        ArrayList<Integer> e4f15 = new ArrayList<>();
	        e4f15.add(0);
	        e4f15.add(5);
	        ArrayList<Integer> e5f15 = new ArrayList<>();
	        e5f15.add(6);
	        e5f15.add(11);
	        ArrayList<Integer> e6f15 = new ArrayList<>();
	        e6f15.add(10);
	        e6f15.add(14);
	        ArrayList<Integer> e7f15 = new ArrayList<>();
	        e7f15.add(12);
	        e7f15.add(8);
	        ArrayList<Integer> e8f15 = new ArrayList<>();
	        e8f15.add(13);
	        e8f15.add(2);
	        ArrayList<ArrayList<Integer>> gf15 = new ArrayList<>();
	        gf15.add(e1f15);
	        gf15.add(e2f15);
	        gf15.add(e3f15);
	        gf15.add(e4f15);
	        gf15.add(e5f15);
	        gf15.add(e6f15);
	        gf15.add(e7f15);
	        gf15.add(e8f15);
	        Date f15 = new Date(gf15);


	        ArrayList<Date> calendar = new ArrayList<>();
	        calendar.add(f1);
	        calendar.add(f2);
	        calendar.add(f3);
	        calendar.add(f4);
	        calendar.add(f5);
	        calendar.add(f6);
	        calendar.add(f7);
	        calendar.add(f8);
	        calendar.add(f9);
	        calendar.add(f10);
	        calendar.add(f11);
	        calendar.add(f12);
	        calendar.add(f13);
	        calendar.add(f14);
	        calendar.add(f15);

	        for (int z = 0; z < calendar.size(); z++) {
	            System.out.println(calendar.get(z).getGames());
	        }

	        float distancia = probando.calculateDistance(calendar);

	        System.out.println("Calendario Rosete Distancia "+distancia);
*/
/*
		ArrayList<Date> calendarioManual = new ArrayList<>(0);

        ArrayList<Integer> e1f1 = new ArrayList<>();
        e1f1.add(0);
        e1f1.add(1);
        ArrayList<Integer> e2f1 = new ArrayList<>();
        e2f1.add(2);
        e2f1.add(3);
        ArrayList<Integer> e3f1 = new ArrayList<>();
        e3f1.add(4);
        e3f1.add(5);
        ArrayList<Integer> e4f1 = new ArrayList<>();
        e4f1.add(6);
        e4f1.add(7);
        ArrayList<ArrayList<Integer>> fecha1 = new ArrayList<>();
        fecha1.add(e1f1);
        fecha1.add(e2f1);
        fecha1.add(e3f1);
        fecha1.add(e4f1);

        Date date1 = new Date(fecha1);

        ArrayList<Integer> e1f2 = new ArrayList<>();
        e1f2.add(1);
        e1f2.add(2);
        ArrayList<Integer> e2f2 = new ArrayList<>();
        e2f2.add(0);
        e2f2.add(3);
        ArrayList<Integer> e3f2 = new ArrayList<>();
        e3f2.add(6);
        e3f2.add(4);
        ArrayList<Integer> e4f2 = new ArrayList<>();
        e4f2.add(5);
        e4f2.add(7);
        ArrayList<ArrayList<Integer>> fecha2 = new ArrayList<>();
        fecha2.add(e1f2);
        fecha2.add(e2f2);
        fecha2.add(e3f2);
        fecha2.add(e4f2);

        Date date2 = new Date(fecha2);

        calendarioManual.add(date1);
        calendarioManual.add(date2);

        System.out.println("Antes de la mutacion: ");
        for (int z = 0; z < calendarioManual.size(); z++) {
            System.out.println(calendarioManual.get(z).getGames());
        }

        int posFirstDate = ThreadLocalRandom.current().nextInt(0, calendarioManual.size()-1);

        int posLastDate = posFirstDate;
        boolean compatible = false;

        while (posLastDate == posFirstDate) {
            posLastDate = ThreadLocalRandom.current().nextInt(0, calendarioManual.size());
        }


        Date firstDate =  calendarioManual.get(posFirstDate);
        System.out.println("Primera fecha: " + firstDate.getGames());

        Date secondDate =  calendarioManual.get(posLastDate);
        System.out.println("Segunada fecha: "+ secondDate.getGames());

        int posFirstDuel = ThreadLocalRandom.current().nextInt(0, firstDate.getGames().size()-1);

        ArrayList<Integer> firstDuel = firstDate.getGames().get(posFirstDuel);
        System.out.println("Primer enfrentamiento: " + firstDuel);

        //ArrayList<ArrayList<Integer>> duels = new ArrayList<>();
        //duels.add(firstDuel);

        int tempSize = secondDate.getGames().size();
        secondDate.getGames().add(firstDuel);
        firstDate.getGames().remove(firstDuel);
        ArrayList<ArrayList<Integer>> results = new ArrayList<>();

        results.add(firstDuel);

        while(!compatible){

            results = Controller.incompatibleDuels(secondDate, results, tempSize);

            if(results.isEmpty()){
                compatible = true;
            }
            else{
                tempSize = firstDate.getGames().size();
                firstDate.getGames().addAll(results);
                secondDate.getGames().removeAll(results);
                results = Controller.incompatibleDuels(firstDate, results, tempSize);
                if (results.isEmpty()){
                    compatible = true;
                }
                else{
                    tempSize = secondDate.getGames().size();
                    secondDate.getGames().addAll(results);
                    firstDate.getGames().removeAll(results);
                }
            }
        }

        System.out.println("Despues de la mutacion: ");
        for (int z = 0; z < calendarioManual.size(); z++) {
            System.out.println(calendarioManual.get(z).getGames());
        }
*/
    }

}
