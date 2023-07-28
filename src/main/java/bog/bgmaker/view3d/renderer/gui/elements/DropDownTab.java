package bog.bgmaker.view3d.renderer.gui.elements;

import bog.bgmaker.view3d.ObjectLoader;
import bog.bgmaker.view3d.managers.MouseInput;
import bog.bgmaker.view3d.managers.RenderMan;
import bog.bgmaker.view3d.managers.WindowMan;
import bog.bgmaker.view3d.renderer.gui.ingredients.LineStrip;
import bog.bgmaker.view3d.utils.Config;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * @author Bog
 */
public class DropDownTab extends Element{

    String tabTitle = "";
    public ArrayList<Element> tabElements;
    public int fontSize;
    boolean extended = true;

    public boolean resizeX = false;
    public boolean resizeY = false;

    public DropDownTab(String id, String tabTitle, Vector2f pos, Vector2f size, int fontSize, RenderMan renderer, ObjectLoader loader, WindowMan window)
    {
        this.id = id;
        this.tabTitle = tabTitle;
        this.pos = pos;
        this.size = size;
        this.fontSize = fontSize;
        this.renderer = renderer;
        this.loader = loader;
        this.window = window;
        tabElements = new ArrayList<>();
    }

    public DropDownTab closed()
    {
        this.extended = false;
        return this;
    }

    public DropDownTab resizeX()
    {
        this.resizeX = true;
        return this;
    }

    public DropDownTab resizeY()
    {
        this.resizeY = true;
        return this;
    }

    public float getFullHeight()
    {
        if(extended)
        {
            float fullSize = size.y;

            for(Element e : tabElements)
                fullSize += e.size == null ? e instanceof DropDownTab.StringElement ? getFontHeight(((StringElement)e).fontSize) : 0 : e.size.y + 3;

            return fullSize;
        }
        else
            return size.y;
    }

    public Element getElementByID(String id)
    {
        for(Element element : tabElements)
            if(element.id.equalsIgnoreCase(id))
                return element;
        return null;
    }

    public void removeElementByID(String id)
    {
        for(int i = 0; i < tabElements.size(); i++)
            if(tabElements.get(i).id.equalsIgnoreCase(id))
                tabElements.remove(i);
    }

    public boolean containsElementByID(String id)
    {
        for(int i = 0; i < tabElements.size(); i++)
            if(tabElements.get(i).id.equalsIgnoreCase(id))
                return true;
        return false;
    }

    public void addButton(String buttonText, Button button)
    {
        if(!containsElementByID(button.id))
        {
            button.buttonText = buttonText;
            button.pos = new Vector2f(0, 0);
            button.size = new Vector2f(size.x - 4, getFontHeight(fontSize) + 4);
            button.fontSize = fontSize;
            button.renderer = renderer;
            button.window = window;
            button.loader = loader;
            tabElements.add(button);
        }
    }

    public void addComboBox(ComboBox comboBox)
    {
        if(!containsElementByID(comboBox.id))
        {
            comboBox.pos = new Vector2f(0, 0);
            comboBox.size = new Vector2f(size.x - 4, getFontHeight(fontSize) + 4);
            comboBox.fontSize = fontSize;
            comboBox.renderer = renderer;
            comboBox.window = window;
            comboBox.loader = loader;
            tabElements.add(comboBox);
        }
    }

    public void addCheckbox(String id, String text)
    {
        if(!containsElementByID(id))
            tabElements.add(new Checkbox(id, text, new Vector2f(0, 0), fontSize, renderer, loader, window));
    }

    public void addCheckbox(String id, String text, boolean checked)
    {
        if(!containsElementByID(id))
        {
            Checkbox checkbox = new Checkbox(id, text, new Vector2f(0, 0), fontSize, renderer, loader, window);
            checkbox.isChecked = checked;
            tabElements.add(checkbox);
        }
    }

    public void addSlider(String id)
    {
        if(!containsElementByID(id))
            tabElements.add(new Slider(id, new Vector2f(0, 0), new Vector2f(size.x - 4, getFontHeight(fontSize) + 4), renderer, loader, window));
    }

    public void addSlider(String id, float sliderPosition, float min, float max)
    {
        if(!containsElementByID(id))
            tabElements.add(new Slider(id, new Vector2f(0, 0), new Vector2f(size.x - 4, getFontHeight(fontSize) + 4), renderer, loader, window, sliderPosition, min, max));
    }

    public void addTextbox(String id)
    {
        if(!containsElementByID(id))
            tabElements.add(new Textbox(id, new Vector2f(0, 0), new Vector2f(size.x - 4, getFontHeight(fontSize) + 4), fontSize, renderer, loader, window));
    }

    public void addTextbox(String id, boolean numbers, boolean letters, boolean others)
    {
        if(!containsElementByID(id))
        {
            Textbox tb = new Textbox(id, new Vector2f(0, 0), new Vector2f(size.x - 4, getFontHeight(fontSize) + 4), fontSize, renderer, loader, window);
            tb.numbers = numbers;
            tb.letters = letters;
            tb.others = others;
            tabElements.add(tb);
        }
    }

    public void addTextbox(String id, String text)
    {
        if(!containsElementByID(id))
        {
            Textbox tb = new Textbox(id, new Vector2f(0, 0), new Vector2f(size.x - 4, getFontHeight(fontSize) + 4), fontSize, renderer, loader, window);
            tb.setText(text);
            tabElements.add(tb);
        }
    }

    public void addTextbox(String id, boolean numbers, boolean letters, boolean others, String text)
    {
        if(!containsElementByID(id))
        {
            Textbox tb = new Textbox(id, new Vector2f(0, 0), new Vector2f(size.x - 4, getFontHeight(fontSize) + 4), fontSize, renderer, loader, window);
            tb.numbers = numbers;
            tb.letters = letters;
            tb.others = others;
            tb.setText(text);
            tabElements.add(tb);
        }
    }

    public void addLabeledTextbox(String id, String label)
    {
        if(!containsElementByID(id))
            tabElements.add(new LabeledTextbox(label, id, new Vector2f(0, 0), new Vector2f(size.x - 4, getFontHeight(fontSize) + 4), fontSize, renderer, loader, window));
    }

    public void addLabeledTextbox(String id, String label, boolean numbers, boolean letters, boolean others)
    {
        if(!containsElementByID(id))
        {
            LabeledTextbox ltb = new LabeledTextbox(label, id, new Vector2f(0, 0), new Vector2f(size.x - 4, getFontHeight(fontSize) + 4), fontSize, renderer, loader, window);
            ltb.textbox.numbers = numbers;
            ltb.textbox.letters = letters;
            ltb.textbox.others = others;
            tabElements.add(ltb);
        }
    }

    public void addLabeledTextbox(String id, String label, String text)
    {

        if(!containsElementByID(id))
        {
            LabeledTextbox ltb = new LabeledTextbox(label, id, new Vector2f(0, 0), new Vector2f(size.x - 4, getFontHeight(fontSize) + 4), fontSize, renderer, loader, window);
            ltb.textbox.setText(text);
            tabElements.add(ltb);
        }
    }

    public void addLabeledTextbox(String id, String label, boolean numbers, boolean letters, boolean others, String text)
    {
        if(!containsElementByID(id))
        {
            LabeledTextbox ltb = new LabeledTextbox(label, id, new Vector2f(0, 0), new Vector2f(size.x - 4, getFontHeight(fontSize) + 4), fontSize, renderer, loader, window);
            ltb.textbox.numbers = numbers;
            ltb.textbox.letters = letters;
            ltb.textbox.others = others;
            ltb.textbox.setText(text);
            tabElements.add(ltb);
        }
    }

    public void addLabeledButton(String id, String label, String buttonText, Button button)
    {
        if(!containsElementByID(id))
            tabElements.add(new LabeledButton(label, id, buttonText, new Vector2f(0, 0), new Vector2f(size.x - 4, getFontHeight(fontSize) + 4), fontSize, button, renderer, loader, window));
    }

    public void addLabeledSlider(String id, String label)
    {
        if(!containsElementByID(id))
            tabElements.add(new LabeledSlider(label, id, new Vector2f(0, 0), new Vector2f(size.x - 4, getFontHeight(fontSize) + 4), fontSize, renderer, loader, window));
    }

    public void addLabeledSlider(String id, String label, float sliderPosition, float min, float max)
    {
        if(!containsElementByID(id))
            tabElements.add(new LabeledSlider(label, id, new Vector2f(0, 0), new Vector2f(size.x - 4, getFontHeight(fontSize) + 4), fontSize, renderer, loader, window, sliderPosition, min, max));
    }

    public void addString(String id, String string)
    {
        if(!containsElementByID(id))
            tabElements.add(new StringElement(id, string, fontSize, renderer, loader, window));
    }

    public void addRect(String id, float height)
    {
        if(!containsElementByID(id))
            tabElements.add(new RectangleElement(id, height, renderer, loader, window));
    }

    public void addRect(String id, float height, Color color)
    {
        if(!containsElementByID(id))
            tabElements.add(new RectangleElement(id, height, color, renderer, loader, window));
    }

    public void addList(String id, ButtonList buttonList, int height)
    {
        if(!containsElementByID(id))
        {
            buttonList.id = id;
            buttonList.pos = new Vector2f(0, 0);
            buttonList.size = new Vector2f(size.x - 4, height);
            tabElements.add(buttonList);
        }
    }

    public boolean dragging = false;
    Vector2f dragOffset = new Vector2f(0, 0);
    @Override
    public void draw(MouseInput mouseInput, boolean overOther) {
        super.draw(mouseInput, overOther);

        if (dragging) {
            this.pos.x = ((float) mouseInput.currentPos.x) - dragOffset.x;
            this.pos.y = ((float) mouseInput.currentPos.y) - dragOffset.y;
        }

        if (!renderer.window.isMinimized) {
            if (this.pos.x < 0)
                this.pos.x = 0;
            if (this.pos.y < 0)
                this.pos.y = 0;

            if (this.pos.x + this.size.x > renderer.window.width)
                this.pos.x = renderer.window.width - this.size.x;
            if (this.pos.y + this.size.y > renderer.window.height)
                this.pos.y = renderer.window.height - this.size.y;
        }

        float yOffset = 0;

        if (extended)
            yOffset = updateElements(yOffset);

        drawRect((int) pos.x, (int) pos.y, (int) size.x, (int) size.y, dragging || (mouseInput.rightButtonPress && isMouseOverTab(mouseInput)) ? Config.INTERFACE_TERTIARY_COLOR : (isMouseOverTab(mouseInput) && !overOther ? Config.INTERFACE_SECONDARY_COLOR : Config.INTERFACE_PRIMARY_COLOR));
        drawRectOutline((int) pos.x, (int) pos.y, (int) size.x, (int) size.y, dragging || (mouseInput.rightButtonPress && isMouseOverTab(mouseInput)) ? Config.INTERFACE_TERTIARY_COLOR2 : (isMouseOverTab(mouseInput) && !overOther ? Config.INTERFACE_SECONDARY_COLOR2 : Config.INTERFACE_PRIMARY_COLOR2), false);

        if (extended)
        {
            drawBackdrop(yOffset);
        }

        if(extended && (resizeX || resizeY))
        {
//         TODO   Vector2f p1 = new Vector2f(pos.x + size.x - 2, pos.y + size.y + yOffset);
//            Vector2f p2 = new Vector2f(p1.x - 10, p1.y);
//            Vector2f p3 = new Vector2f(p1.x, p1.y - 10);
//            boolean mouseInTriangle = Utils.pointInTriangle2D(new Vector2f((float) mouseInput.currentPos.x, (float) mouseInput.currentPos.y), p1, p2, p3);
//            drawTriangle(p1, p2, p3, mouseInTriangle ? Color.blue : new Color(0f, 0f, 0f, 0.5f));
        }

        drawString(tabTitle, Config.FONT_COLOR, (int) (pos.x + size.y / 2 - getFontHeight(fontSize) / 2), (int) (pos.y + size.y / 2 - getFontHeight(fontSize) / 2), fontSize);

        if(extended)
        {
            Vector2f p1 = new Vector2f(pos.x + size.x - size.y * 0.35f, pos.y + size.y * 0.25f);
            Vector2f p2 = new Vector2f(p1.x - size.y / 2f, p1.y);
            Vector2f p3 = new Vector2f(p1.x - size.y / 4f, pos.y + size.y * 0.75f);
            drawTriangle(p1, p2, p3, Config.FONT_COLOR);

            drawElements(mouseInput, overOther);
        }
        else
        {
            Vector2f p1 = new Vector2f(pos.x + size.x - size.y * 0.35f, pos.y + size.y / 2f);
            Vector2f p2 = new Vector2f(p1.x - size.y / 2f, pos.y + size.y * 0.25f);
            Vector2f p3 = new Vector2f(p1.x - size.y / 2f, pos.y + size.y * 0.75f);
            drawTriangle(p1, p2, p3, Config.FONT_COLOR);
        }
    }

    public void drawBackdrop(float yOffset)
    {
        drawRect((int) pos.x, (int) (pos.y + size.y), (int) size.x, (int) (2f + yOffset), Config.PRIMARY_COLOR);
        drawRectOutline((int) pos.x, (int) (pos.y + size.y), (int) size.x, (int) (2f + yOffset), Config.SECONDARY_COLOR, false, LineStrip.UP);
    }

    public void drawElements(MouseInput mouseInput, boolean overOther)
    {
        ArrayList<Integer> combos = new ArrayList<>();
        ArrayList<Boolean> combosB = new ArrayList<>();

        for(int i = 0; i < tabElements.size(); i++)
        {
            Element element = tabElements.get(i);

            if(element instanceof ComboBox)
            {
                combos.add(i);
                combosB.add(overOther);
                if(element.isMouseOverElement(mouseInput))
                    overOther = true;
            }
            else
            {
                element.draw(mouseInput, overOther);
                if(element.isMouseOverElement(mouseInput))
                    overOther = true;
            }
        }

        for(int i = combos.size() - 1; i >= 0; i--)
            tabElements.get(combos.get(i)).draw(mouseInput, combosB.get(i));
    }

    public float updateElements(float yOffset)
    {
        for (int i = 0; i < tabElements.size(); i++) {
            Element element = tabElements.get(i);
            element.pos = new Vector2f(pos.x + (element instanceof ButtonList ? 0 : 2), pos.y + size.y + 2 + yOffset);

            if (element.size == null)
                element.size = new Vector2f(size.x - (element instanceof ButtonList ? 0 : 4), getFontHeight(fontSize) + 4);
            else
                element.size = new Vector2f(size.x - (element instanceof ButtonList ? 0 : 4), element.size.y);

            yOffset += element.size.y + 2;
        }
        if (resizeX || resizeY)
            yOffset += 12;

        return yOffset;
    }

    @Override
    public void secondThread() {
        super.secondThread();

        if(extended)
            for(Element element : tabElements)
                element.secondThread();
    }

    @Override
    public void onChar(int codePoint, int modifiers) {
        super.onChar(codePoint, modifiers);

        if(extended)
            for(Element element : tabElements)
                element.onChar(codePoint, modifiers);
    }

    @Override
    public void onMouseScroll(Vector2d pos, double xOffset, double yOffset) {
        super.onMouseScroll(pos, xOffset, yOffset);

        if(extended)
            for(Element element : tabElements)
                element.onMouseScroll(pos, xOffset, yOffset);
    }

    public boolean isMouseOverTab(Vector2f mousePos)
    {
        return mousePos.x > pos.x && mousePos.y > pos.y && mousePos.x < pos.x + size.x && mousePos.y < pos.y + size.y;
    }
    public boolean isMouseOverTab(Vector2d mousePos)
    {
        return isMouseOverTab(new Vector2f((float) mousePos.x, (float) mousePos.y));
    }
    public boolean isMouseOverTab(MouseInput mouseInput)
    {
        return isMouseOverTab(mouseInput.currentPos);
    }
    @Override
    public boolean isMouseOverElement(Vector2f mousePos) {
        float yOffset = 0;

        try
        {
            if(extended)
                for(int i = 0; i < tabElements.size(); i++)
                {
                    Element element = tabElements.get(i);
                    yOffset += element.size.y + 2;
                }
        }catch (Exception e){}

        return mousePos.x > pos.x && mousePos.y > pos.y && mousePos.x < pos.x + size.x && mousePos.y < pos.y + size.y + (extended ? 2 + yOffset : 0);
    }

    @Override
    public void onKey(int key, int scancode, int action, int mods) {
        super.onKey(key, scancode, action, mods);

        if(extended)
        {
            boolean foundNext = false;

            for(int i = 0; i < tabElements.size(); i++)
            {
                Element element = tabElements.get(i);

                if(key == GLFW.GLFW_KEY_TAB && action == GLFW.GLFW_PRESS && (element instanceof Textbox || element instanceof LabeledTextbox || element instanceof Textarea))
                {
                    if(element.isFocused() && !foundNext)
                    {
                        element.setFocused(false);

                        int o = i + 1;

                        while(!foundNext)
                        {
                            if(o == tabElements.size())
                                o = 0;

                            Element nelement = tabElements.get(o);

                            if(nelement instanceof Textbox || nelement instanceof LabeledTextbox || nelement instanceof Textarea)
                            {
                                nelement.setFocused(true);
                                foundNext = true;
                            }

                            o++;
                        }
                    }
                }

                element.onKey(key, scancode, action, mods);
            }
        }
    }

    @Override
    public void onClick(Vector2d pos, int button, int action, int mods, boolean overOther) {

        if(isMouseOverTab(pos) && !overOther)
        {
            if(button == GLFW.GLFW_MOUSE_BUTTON_2 && action == GLFW.GLFW_PRESS)
            {
                extended = !extended;

                if(!extended)
                    for(Element element : tabElements)
                        element.setFocused(false);
            }
            else if(button == GLFW.GLFW_MOUSE_BUTTON_1)
                if(action == GLFW.GLFW_PRESS)
                {
                    dragging = true;
                    dragOffset = new Vector2f((float) (pos.x - this.pos.x), (float) (pos.y - this.pos.y));
                }
        }

        if(button == GLFW.GLFW_MOUSE_BUTTON_1 && action == GLFW.GLFW_RELEASE)
                dragging = false;

        if(extended)
            for(Element element : tabElements)
            {
                element.onClick(pos, button, action, mods, overOther);
                if(element.isMouseOverElement(pos))
                    overOther = true;
            }

        super.onClick(pos, button, action, mods, overOther);
    }

    @Override
    public boolean isFocused() {
        boolean focused = false;
        for(Element element : tabElements)
            if(element.isFocused())
                focused = true;

        return extended && focused;
    }

    public static class LabeledTextbox extends Element
    {
        String label = "";
        public Textbox textbox;
        int fontSize;

        public LabeledTextbox(String label, String id, Vector2f pos, Vector2f size, int fontSize, RenderMan renderer, ObjectLoader loader, WindowMan window)
        {
            this.id = id;
            this.pos = pos;
            this.size = size;
            this.fontSize = fontSize;
            this.renderer = renderer;
            this.loader = loader;
            this.window = window;

            LabeledTextbox these = this;
            this.textbox = new Textbox(id, pos, size, fontSize, renderer, loader, window)
            {
                @Override
                public Color textColor() {
                    return these.textColor();
                }
            };
            this.label = label;
        }

        @Override
        public void draw(MouseInput mouseInput, boolean overElement) {
            super.draw(mouseInput, overElement);

            drawString(label, textColor(), (int) (pos.x + size.y / 2 - getFontHeight(fontSize) / 2), (int) (pos.y + size.y / 2 - getFontHeight(fontSize) / 2), fontSize);
            textbox.pos = new Vector2f(pos.x + 5*(Math.round((getStringWidth(label, fontSize))/5)), pos.y);
            textbox.size = new Vector2f(size.x - 5*(Math.round((getStringWidth(label, fontSize))/5)), size.y);
            textbox.draw(mouseInput, overElement);
        }

        @Override
        public void onKey(int key, int scancode, int action, int mods) {
            super.onKey(key, scancode, action, mods);

            textbox.onKey(key, scancode, action, mods);
        }

        @Override
        public void onChar(int codePoint, int modifiers) {
            super.onChar(codePoint, modifiers);

            textbox.onChar(codePoint, modifiers);
        }

        @Override
        public void onClick(Vector2d pos, int button, int action, int mods, boolean overElement) {
            super.onClick(pos, button, action, mods, overElement);

            textbox.onClick(pos, button, action, mods, overElement);
        }

        public Color textColor()
        {
            return Config.FONT_COLOR;
        }

        @Override
        public boolean isFocused() {
            return textbox.isFocused();
        }

        @Override
        public void setFocused(boolean focused) {
            this.textbox.setFocused(focused);
            super.setFocused(focused);
        }
    }

    public static class LabeledButton extends Element
    {
        String label = "";
        public Button button;
        int fontSize;

        public LabeledButton(String label, String id, String buttonText, Vector2f pos, Vector2f size, int fontSize, Button button, RenderMan renderer, ObjectLoader loader, WindowMan window)
        {
            this.id = id;
            this.pos = pos;
            this.size = size;
            this.fontSize = fontSize;
            this.renderer = renderer;
            this.loader = loader;
            this.window = window;
            button.id = id;
            button.buttonText = buttonText;
            button.pos = pos;
            button.size = size;
            button.fontSize = fontSize;
            button.renderer = renderer;
            button.loader = loader;
            button.window = window;
            this.button = button;
            this.label = label;
        }

        @Override
        public void draw(MouseInput mouseInput, boolean overElement) {
            super.draw(mouseInput, overElement);

            drawString(label, Config.FONT_COLOR, (int) (pos.x + size.y / 2 - getFontHeight(fontSize) / 2), (int) (pos.y + size.y / 2 - getFontHeight(fontSize) / 2), fontSize);
            button.pos = new Vector2f(pos.x + 5 * (Math.round((getStringWidth(label, fontSize))/5)), pos.y);
            button.size = new Vector2f(size.x - 5 * (Math.round((getStringWidth(label, fontSize))/5)), size.y);
            button.draw(mouseInput, overElement);
        }

        @Override
        public void onKey(int key, int scancode, int action, int mods) {
            super.onKey(key, scancode, action, mods);

            button.onKey(key, scancode, action, mods);
        }

        @Override
        public void onChar(int codePoint, int modifiers) {
            super.onChar(codePoint, modifiers);

            button.onChar(codePoint, modifiers);
        }

        @Override
        public void onClick(Vector2d pos, int button, int action, int mods, boolean overElement) {
            super.onClick(pos, button, action, mods, overElement);

            this.button.onClick(pos, button, action, mods, overElement);
        }
    }

    public static class LabeledSlider extends Element
    {
        String label = "";
        public Slider slider;
        int fontSize;

        public LabeledSlider(String label, String id, Vector2f pos, Vector2f size, int fontSize, RenderMan renderer, ObjectLoader loader, WindowMan window)
        {
            this.id = id;
            this.pos = pos;
            this.size = size;
            this.fontSize = fontSize;
            this.renderer = renderer;
            this.loader = loader;
            this.window = window;
            this.slider = new Slider(id, pos, size, renderer, loader, window);
            this.label = label;
        }

        public LabeledSlider(String label, String id, Vector2f pos, Vector2f size, int fontSize, RenderMan renderer, ObjectLoader loader, WindowMan window, float sliderPos, float min, float max)
        {
            this.id = id;
            this.pos = pos;
            this.size = size;
            this.fontSize = fontSize;
            this.renderer = renderer;
            this.loader = loader;
            this.slider = new Slider(id, pos, size, renderer, loader, window, sliderPos, min, max);
            this.label = label;
        }

        @Override
        public void draw(MouseInput mouseInput, boolean overElement) {
            super.draw(mouseInput, overElement);

            drawString(label, Config.FONT_COLOR, (int) (pos.x + size.y / 2 - getFontHeight(fontSize) / 2), (int) (pos.y + size.y / 2 - getFontHeight(fontSize) / 2), fontSize);
            slider.pos = new Vector2f(pos.x + 5*(Math.round((getStringWidth(label, fontSize))/5)), pos.y);
            slider.size = new Vector2f(size.x - 5*(Math.round((getStringWidth(label, fontSize))/5)), size.y);
            slider.draw(mouseInput, overElement);
        }

        @Override
        public void onKey(int key, int scancode, int action, int mods) {
            super.onKey(key, scancode, action, mods);

            slider.onKey(key, scancode, action, mods);
        }

        @Override
        public void onChar(int codePoint, int modifiers) {
            super.onChar(codePoint, modifiers);

            slider.onChar(codePoint, modifiers);
        }

        @Override
        public void onClick(Vector2d pos, int button, int action, int mods, boolean overElement) {
            super.onClick(pos, button, action, mods, overElement);

            this.slider.onClick(pos, button, action, mods, overElement);
        }

        @Override
        public void setFocused(boolean focused) {
            slider.setFocused(focused);
            super.setFocused(focused);
        }
    }

    public static class StringElement extends  Element
    {
        public String string = "";
        int fontSize;

        public StringElement(String id, String string, int fontSize, RenderMan renderer, ObjectLoader loader, WindowMan window)
        {
            this.string = string;
            this.id = id;
            this.fontSize = fontSize;
            this.renderer = renderer;
            this.loader = loader;
            this.window = window;
        }

        @Override
        public void draw(MouseInput mouseInput, boolean overElement) {
            super.draw(mouseInput, overElement);

            try{drawString(string, Config.FONT_COLOR, (int) (pos.x + size.y / 2 - getFontHeight(fontSize) / 2), (int) (pos.y + size.y / 2 - getFontHeight(fontSize) / 2), fontSize);}catch (Exception e){}
        }
    }

    public static class RectangleElement extends Element
    {

        public RectangleElement(String id, float height, RenderMan renderer, ObjectLoader loader, WindowMan window)
        {
            this.id = id;
            this.size = new Vector2f(0, height);
            this.renderer = renderer;
            this.loader = loader;
            this.window = window;
        }

        public RectangleElement(String id, float height, Color separatorColor, RenderMan renderer, ObjectLoader loader, WindowMan window)
        {
            this.id = id;
            this.size = new Vector2f(0, height);
            this.renderer = renderer;
            this.loader = loader;
            this.window = window;
        }

        @Override
        public void draw(MouseInput mouseInput, boolean overElement) {
            super.draw(mouseInput, overElement);

            drawRect((int) pos.x, (int) pos.y, (int) size.x, (int) size.y, Config.PRIMARY_COLOR);
            drawRectOutline((int) pos.x, (int) pos.y, (int) size.x, (int) size.y, Config.SECONDARY_COLOR, false);
        }
    }
}
