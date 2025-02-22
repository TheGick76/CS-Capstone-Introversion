import javax.swing.*;

public class Energy implements Runnable {

    public Himothy player ;
    public Board board ;
    public JProgressBar energy ;

    Energy(Himothy player, Board board, JProgressBar energy) {

        this.player = player ;
        this.board = board ;
        this.energy = energy ;

    }

    public void run() {

        int currentEnergy = energy.getValue() ;
        int baseEnergyChange = 1 ;
        int extraEnergyChange = 0 ;

        // Keeps going forever
        // In the future, may add conditional here
        // e.g. if currentEnergy == 0
        while (true) {

            // Wait for 1 second
            try {
                Thread.sleep(1000) ;
            } catch (InterruptedException e) {
                System.out.println(e);
            }

            // Check the tileType of player's current position
            // Change extraEnergyChange based on that amount
            switch(board.BoardTiles[player.currentTilePosition].tileType){

                case("EMPTY") -> {
                    extraEnergyChange = 0 ;
                }

                case("PERSON") -> {
                    extraEnergyChange = 1 ;
                }

                case("CAT") -> {
                    extraEnergyChange = -10 ;
                }
            }

            // change energy based on standard energy change and extra energy change amounts
            currentEnergy -= (baseEnergyChange + extraEnergyChange) ;

            energy.setValue(currentEnergy) ;

        }

    }


}