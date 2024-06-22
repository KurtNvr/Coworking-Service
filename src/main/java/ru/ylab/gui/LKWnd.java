package ru.ylab.gui;

import ru.ylab.database.Repository;
import ru.ylab.model.Place;
import ru.ylab.model.User;
import ru.ylab.util.GraphicUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LKWnd extends JFrame {

    private final User user;

    private JPanel rootPanel;
    private JLabel loginField;
    private JLabel fullNameField;
    private JComboBox<String> sortingField;
    private JButton addWorkButton;
    private JButton addConfButton;
    private JButton reserveButton;
    private JButton cancelReserveButton;
    private JList<Long> placeIdListField;
    private JList<Place.PlaceType> placeTypeListField;
    private JList<LocalDateTime> placeDateListField;

    public LKWnd(User user) {
        this.user = user;
        sortingField.addItem("ID");
        sortingField.addItem("Date");
        loginField.setText(user.getLogin());
        fullNameField.setText("(" + user.getLastName() + " " + user.getFirstName() + ")");

        Place[] array = Repository.getInstance().getPlaces().values().toArray(Place[]::new);
        Long[] ids = new Long[array.length];
        Place.PlaceType[] types = new Place.PlaceType[array.length];
        LocalDateTime[] dates = new LocalDateTime[array.length];

        for(int i = 0; i < array.length; i++) {
            ids[i] = array[i].getId();
            types[i] = array[i].getType();
            dates[i] = array[i].getReservationDate();
        }

        placeIdListField.setListData(ids);
        placeTypeListField.setListData(types);
        placeDateListField.setListData(dates);

        setContentPane(rootPanel);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(640, 480);
        setTitle("Coworking Service: Main Window");
        GraphicUtil.setCenteredToScreen(this);
        addWorkButton.addActionListener(this::onAddWork);
        addConfButton.addActionListener(this::onAddConf);
        reserveButton.addActionListener(this::onReserve);
        cancelReserveButton.addActionListener(this::onCancelReserve);
        sortingField.addActionListener(this::onSorting);
    }

    private void onAddWork(ActionEvent event) {
        Repository.getInstance().create(Place.builder().type(Place.PlaceType.WORK).build());
        refresh();
        JOptionPane.showMessageDialog(this, "Add work", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onAddConf(ActionEvent event) {
        Repository.getInstance().create(Place.builder().type(Place.PlaceType.CONF_HALL).build());
        refresh();
        JOptionPane.showMessageDialog(this, "Add conf", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onReserve(ActionEvent event) {
        JOptionPane.showMessageDialog(this, "Add reserve", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onCancelReserve(ActionEvent event) {
        JOptionPane.showMessageDialog(this, "Cancel reserve", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onSorting(ActionEvent event) {
        String sorting = sortingField.getSelectedItem().toString();
        if (sorting.equalsIgnoreCase("Date")) {
            List<Place> places = Repository.getInstance().getPlaces().values().stream().toList();
            List<Place> temp = new ArrayList<>(places);
            temp.sort(Comparator.comparing(Place::getReservationDate));
            refresh();
            JOptionPane.showMessageDialog(this, "Sorting by date", "Success", JOptionPane.INFORMATION_MESSAGE);
        } else if (sorting.equalsIgnoreCase("ID")) {
            List<Place> places = Repository.getInstance().getPlaces().values().stream().toList();
            List<Place> temp = new ArrayList<>(places);
            temp.sort(Comparator.comparing(Place::getId));
            refresh();
            JOptionPane.showMessageDialog(this, "Sorting by id", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void refresh() {
        Place[] array = Repository.getInstance().getPlaces().values().toArray(Place[]::new);
        Long[] ids = new Long[array.length];
        Place.PlaceType[] types = new Place.PlaceType[array.length];
        LocalDateTime[] dates = new LocalDateTime[array.length];

        for(int i = 0; i < array.length; i++) {
            ids[i] = array[i].getId();
            types[i] = array[i].getType();
            dates[i] = array[i].getReservationDate();
        }

        placeIdListField.setListData(ids);
        placeTypeListField.setListData(types);
        placeDateListField.setListData(dates);
    }
}
