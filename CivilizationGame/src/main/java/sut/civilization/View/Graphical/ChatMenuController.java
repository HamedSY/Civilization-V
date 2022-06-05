package sut.civilization.View.Graphical;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Popup;
import javafx.stage.Window;
import sut.civilization.Civilization;
import sut.civilization.Enums.Menus;
import sut.civilization.Model.Classes.Chat;
import sut.civilization.Model.Classes.Game;
import sut.civilization.Model.Classes.Message;
import sut.civilization.Model.Classes.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ChatMenuController extends ViewController {
    public BorderPane wholeBorderPane;
    public TextArea messageInput;
    public TextField search;
    public VBox allMessages;
    public ScrollPane messagesScrollPane;
    public VBox allChats;
    private Chat selectedChat = null;
    private ArrayList<User> selectedMembersForGroup = new ArrayList<>();


    public void BackToMainMenu() {
        Game.changeScene(Menus.MAIN_MENU);
    }

    @FXML
    private void initialize() {

//        Game.getLoggedInUser().addChat(new Chat(Game.getUsers().get(0)));
//        for (Chat chat : Game.getLoggedInUser().getChats()) {
//            listAChat(chat.getUser());
//        }
    }

    public void send() {
        if (!messageInput.getText().isBlank() && selectedChat != null) {
            VBox messageBox = new VBox();
            Label name = new Label(Game.getLoggedInUser().getUsername());
            name.getStyleClass().add("name");
            Label messageText = new Label(messageInput.getText());
            messageText.getStyleClass().add("message");
            Label time = new Label(LocalDateTime.now().getHour() + ":" + LocalDateTime.now().getMinute());
            time.getStyleClass().add("time");
            messageBox.getChildren().add(name);
            messageBox.getChildren().add(messageText);
            messageBox.getChildren().add(time);
            messageBox.getStyleClass().add("messageBox");
            messageBox.setMaxWidth(Region.USE_PREF_SIZE);
            messageBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
            VBox.setMargin(messageBox, new Insets(10, 0, 0, 0));

            ImageView avatar = new ImageView(new Image(Civilization.class.getResourceAsStream("/sut/civilization/Images/user-profile.png")));
            avatar.getStyleClass().add("avatar");

            HBox message = new HBox();
            message.getChildren().add(avatar);
            message.getChildren().add(messageBox);
            message.setAlignment(Pos.BOTTOM_RIGHT);
            message.setStyle("-fx-padding: 0 0 15 0;");

            allMessages.getChildren().add(message);
            messageInput.setText("");
            messagesScrollPane.setVvalue(1);

            selectedChat.addMessage(new Message(Game.getLoggedInUser(), messageText.getText(), time.getText()));
        }
    }

    public void sendByEnter(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ENTER) send();
    }

    public void search() {
        allChats.getChildren().clear();
        int i = 0;
        for (User user : Game.getUsers()){
            if (user.getUsername().startsWith(search.getText()) && user != Game.getLoggedInUser()) {
                listAChat(user, i);
                i += 2;
            }
        }
    }

    private void listAChat(User user, int i) {
        //fixme avatar
        ImageView avatar = new ImageView(new Image(Civilization.class.getResourceAsStream("/sut/civilization/Images/user-profile.png")));
        Label name = new Label(user.getUsername());
        name.getStyleClass().add("name");
        name.getStyleClass().add("chatPreview");
        //fixme message preview
        Label message = new Label("message preview...");
//                Label message = new Label(Game.getLoggedInUser().getChatByUsername(user.getUsername()).getMessages().get(
//                        Game.getLoggedInUser().getChatByUsername(user.getUsername()).getMessages().size() - 1
//                ).getMessageText());
        message.getStyleClass().add("chatPreview");
        VBox nameAndMessageVBox = new VBox(name, message);
        //fixme message number
        Label messageNumber = new Label("10");
        messageNumber.getStyleClass().add("chatPreview");
        //fixme time
        Label time= new Label("10:20");
        time.getStyleClass().add("chatPreview");
        VBox numberAndTimeVBox = new VBox(messageNumber, time);
        numberAndTimeVBox.setAlignment(Pos.CENTER);
        HBox chatPreview = new HBox(avatar, nameAndMessageVBox, numberAndTimeVBox);

        allChats.getChildren().add(chatPreview);
        allChats.getChildren().add(new Separator());

        chatPreview.setOnMouseClicked(mouseEvent -> selectChat(user, i));
    }

    private void selectChat(User user, int i) {
        boolean isChatFound = false;
        if (Game.getLoggedInUser().getChats() != null)
            for (Chat chat : Game.getLoggedInUser().getChats())
                if (chat.getUser1().getUsername().equals(user.getUsername()) ||
                        chat.getUser2().getUsername().equals(user.getUsername())) {
                    selectedChat = chat;
                    isChatFound = true;
                    loadMessages(selectedChat);
                }

        if (!isChatFound) {
            selectedChat = new Chat(Game.getLoggedInUser(), user);
            Game.getLoggedInUser().addChat(selectedChat);
            user.addChat(selectedChat);
            loadMessages(selectedChat);
        }

        for (int j = 0; j < allChats.getChildren().size(); j++) {
            allChats.getChildren().get(j).setStyle("-fx-background-color: white;");
        }
        allChats.getChildren().get(i).setStyle("-fx-background-color: gray;");

    }

    private void loadMessages(Chat chat) {
        allMessages.getChildren().clear();
        if (chat.getMessages() != null) {
            for (Message message : chat.getMessages()) {
                VBox messageBox = new VBox();
                Label name = new Label(message.getSender().getUsername());
                name.getStyleClass().add("name");
                Label messageText = new Label(message.getMessageText());
                messageText.getStyleClass().add("message");
                Label time = new Label(message.getTime());
                time.getStyleClass().add("time");
                messageBox.getChildren().add(name);
                messageBox.getChildren().add(messageText);
                messageBox.getChildren().add(time);
                messageBox.getStyleClass().add("messageBox");
                if (message.getSender() != Game.getLoggedInUser()) messageBox.setStyle("-fx-background-color: #cbe6f7;");
                messageBox.setMaxWidth(Region.USE_PREF_SIZE);
                messageBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
                VBox.setMargin(messageBox, new Insets(10, 0, 0, 0));

                //fixme avatar
                ImageView avatar = new ImageView(new Image(Civilization.class.getResourceAsStream("/sut/civilization/Images/user-profile.png")));
                avatar.getStyleClass().add("avatar");

                HBox hBox = new HBox();
                hBox.getChildren().add(avatar);
                hBox.getChildren().add(messageBox);
                hBox.setAlignment(Pos.BOTTOM_RIGHT);
                hBox.setStyle("-fx-padding: 0 0 15 0;");

                allMessages.getChildren().add(hBox);
                messagesScrollPane.setVvalue(1);
            }
        }
    }

    public void newGroup() {
        Window window = Game.getCurrentScene().getWindow();
        Popup createGroupPopup = new Popup();
        TextField groupSearch = new TextField();
        groupSearch.setPrefWidth(200);
        groupSearch.setPadding(new Insets(10));
        ImageView searchIcon = new ImageView(new Image(Civilization.class.getResourceAsStream("/sut/civilization/Images/search.png")));
        searchIcon.setFitHeight(32);
        searchIcon.setFitWidth(32);
        HBox searchHBox = new HBox(groupSearch, searchIcon);
        VBox groupMemberList = new VBox();
        ScrollPane groupScrollPane = new ScrollPane(groupMemberList);
        Button cancelButton = new Button("cancel");
        Button createButton = new Button("Create");
        cancelButton.setStyle("-fx-background-color: red");
        createButton.setStyle("-fx-background-color: green");
        HBox buttonsHBox = new HBox(cancelButton, createButton);
        VBox popupVBox = new VBox(searchHBox, groupScrollPane, buttonsHBox);
        popupVBox.setPrefWidth(320);
        popupVBox.setPrefHeight(400);


        createGroupPopup.getContent().add(popupVBox);
        createGroupPopup.setX(window.getWidth() / 2);
        createGroupPopup.setY((window.getHeight() / 2) - 200);
        createGroupPopup.show(window);
        wholeBorderPane.setEffect(new Lighting());

        searchIcon.setOnMouseClicked(mouseEvent -> {
            groupMemberList.getChildren().clear();
            selectedMembersForGroup.clear();
            int i = 0;
            for (User user : Game.getUsers()){
                if (user.getUsername().startsWith(groupSearch.getText()) && user != Game.getLoggedInUser()) {
                    searchMemberForGroup(user, groupMemberList, i);
                    i += 2;
                }
            }
        });

        createButton.setOnMouseClicked(mouseEvent -> {
            createGroupPopup.hide();
        });

        cancelButton.setOnMouseClicked(mouseEvent -> {
            createGroupPopup.hide();
        });
    }


    private void searchMemberForGroup(User user, VBox groupMemberList, int i) {
        //fixme avatar
        ImageView avatar = new ImageView(new Image(Civilization.class.getResourceAsStream("/sut/civilization/Images/user-profile.png")));
        Label name = new Label(user.getUsername());
        name.getStyleClass().add("name");
        name.getStyleClass().add("chatPreview");
        HBox memberPreview = new HBox(avatar, name);
        memberPreview.setPrefWidth(300);

        groupMemberList.getChildren().add(memberPreview);
        groupMemberList.getChildren().add(new Separator());

        memberPreview.setOnMouseClicked(mouseEvent -> {
            if (selectedMembersForGroup.contains(user)) {
                groupMemberList.getChildren().get(i).setStyle("-fx-background-color: white;");
                selectedMembersForGroup.remove(user);
            } else {
                groupMemberList.getChildren().get(i).setStyle("-fx-background-color: green;");
                selectedMembersForGroup.add(user);
            }
        });
    }

}