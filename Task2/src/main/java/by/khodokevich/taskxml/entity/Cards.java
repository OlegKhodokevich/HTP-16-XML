package by.khodokevich.taskxml.entity;


import java.util.ArrayList;

public class Cards {
    private ArrayList<Postcard> list = new ArrayList<>();

    public Cards() {
        super();
    }

    public void setList(ArrayList<Postcard> list) {
        this.list = list;
    }

    public boolean add(Postcard card) {
        return list.add(card);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{list=").append(list).append("}");
        return sb.toString();
    }
}
