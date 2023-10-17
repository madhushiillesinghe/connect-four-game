package lk.ijse.dep.service;
//fully abstarct time use the interface
public interface BoardUI {
    //interface infinity the public abstract for any  method
    void update(int col, boolean isHuman);

    void notifyWinner(Winner winner);
}
