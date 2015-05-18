package view;

import java.io.File;

import shared.enums.CardShape;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CardView extends ImageView{

    private final static Image CARD1_IMAGE = new Image(
            new File("images/Card - Circle.png").toURI().toString());
    private final static Image CARD2_IMAGE = new Image(
            new File("images/Card - Square.png").toURI().toString());
    private final static Image CARD3_IMAGE = new Image(
            new File("images/Card - Triangle.png").toURI().toString());
    
    private CardShape card;
    
    public CardView() {
        setCard(CardShape.NOCARD);
    }
    public CardView(CardShape c) {
        setCard(c);
    }
    
    public void setCard(CardShape c) {
        if (card != c) {
            card = c;
            
            switch (c) {
            case NOCARD:
                setImage(null);
                break;
            case SHAPE1:
                setImage(CARD1_IMAGE);
                break;
            case SHAPE2:
                setImage(CARD2_IMAGE);
                break;
            case SHAPE3:
                setImage(CARD3_IMAGE);
                break;
            default:
                setImage(null);
                break;
            }
        }
    }
    
    public CardShape getCard() {
        return card;
    }
}
