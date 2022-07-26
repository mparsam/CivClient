package View.Components;

import Model.User;
import View.Network;
import enums.RequestActions;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import net.bytebuddy.utility.RandomString;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class Civ6Profile extends VBox {
    private final float width = 300, height = 200;
    private final ImageView photo;
    private final Text name;

    public Civ6Profile(User user) {
        this.setWidth(width);
        this.setHeight(height);
        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        photo = getImageOfUser(user);
        photo.setFitWidth(300); // TODO: 6/3/2022 add border around picture
        photo.setFitHeight(175);
        name = new Text(user.getUsername() + " (aka." + user.getNickname() + ")");
        name.setFont(Font.loadFont(getClass().getClassLoader().getResource("fonts/Menu.ttf").toExternalForm(), 48));
        name.setFill(Color.WHITE);
        name.setFont(Font.font(20));
        this.getChildren().addAll(photo, name);
    }

    private ImageView getImageOfUser(User user) {
        try {
            String filepath = user.getUsername() + "_" + RandomString.make(3) + ".png";
            ArrayList<Byte> bytes = (ArrayList<Byte>) Network.getResponseObjOf(RequestActions.GET_USERS_PROFILE_PIC.code, null);
            File f = new File("src/main/resources/images/myProfilePic/" + filepath);

            f.getParentFile().mkdirs();
            f.createNewFile();
            FileOutputStream oos = new FileOutputStream(f);
            byte[] raw = new byte[bytes.size()];
            for (int i = 0; i < bytes.size(); i++) {
                raw[i] = bytes.get(i);
            }
            oos.write(raw);
            oos.flush();
            oos.close();
            while (!f.exists()) {

            }
//            getClass().
//            System.out.println(User.class.getResource("/images/myProfilePic/" +"1.png").toExternalForm());
            ImageView img = new ImageView(new Image(f.toURI().toString()));
            f.delete();
            return img;
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
        return null;
    }

    public void getfileName() {
        System.err.println(photo.getImage().getUrl());
    }
}
