package xyz.ravis96.dreamfreeze.helpers;

import lombok.RequiredArgsConstructor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.ravis96.dreamfreeze.utils.ChatUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ItemReplacer {

    private final ItemStack is;

    public ItemStack fixColors() {
        ItemStack is = new ItemStack(this.is);
        ItemMeta im = is.getItemMeta();
        assert im != null;

        if(im.hasDisplayName()) {
            im.setDisplayName(ChatUtil.fixColor(im.getDisplayName()));
        }

        if(im.hasLore()) {
            im.setLore(Objects.requireNonNull(im.getLore()).stream().map(ChatUtil::fixColor).collect(Collectors.toList()));
        }

        is.setItemMeta(im);
        return is;
    }

    public ItemStack setAndFixColors(String name) {
        ItemStack is = new ItemStack(this.is);
        ItemMeta im = is.getItemMeta();
        assert im != null;

        im.setDisplayName(ChatUtil.fixColor(name));

        if(im.hasLore()) {
            im.setLore(Objects.requireNonNull(im.getLore()).stream().map(ChatUtil::fixColor).collect(Collectors.toList()));
        }

        is.setItemMeta(im);
        return is;
    }

    public ItemStack setAndFixColors(List<String> lore) {
        ItemStack is = new ItemStack(this.is);
        ItemMeta im = is.getItemMeta();
        assert im != null;

        if(im.hasDisplayName()) {
            im.setDisplayName(ChatUtil.fixColor(im.getDisplayName()));
        }

        im.setLore(Objects.requireNonNull(lore).stream().map(ChatUtil::fixColor).collect(Collectors.toList()));

        is.setItemMeta(im);
        return is;
    }

    public ItemStack setAndFixColors(String name, List<String> lore) {
        ItemStack is = new ItemStack(this.is);
        ItemMeta im = is.getItemMeta();
        assert im != null;

        im.setDisplayName(ChatUtil.fixColor(name));

        im.setLore(Objects.requireNonNull(lore).stream().map(ChatUtil::fixColor).collect(Collectors.toList()));

        is.setItemMeta(im);
        return is;
    }

    public ItemStack fixColorsWithReplace(Map<String, String> map) {
        ItemStack is = new ItemStack(this.is);
        ItemMeta im = is.getItemMeta();
        assert im != null;

        if(im.hasDisplayName()) {
            im.setDisplayName(ChatUtil.fixColor(im.getDisplayName()));
        }

        if(im.hasLore()) {
            im.setLore(Objects.requireNonNull(im.getLore()).stream()
                    .map(s -> ChatUtil.fixColor(this.replaceFromMap(s, map))).collect(Collectors.toList()));
        }

        is.setItemMeta(im);
        return is;
    }

    public ItemStack setAndFixColorsWithReplace(String name, Map<String, String> map) {
        ItemStack is = new ItemStack(this.is);
        ItemMeta im = is.getItemMeta();
        assert im != null;

        im.setDisplayName(ChatUtil.fixColor(this.replaceFromMap(name, map)));

        if(im.hasLore()) {
            im.setLore(Objects.requireNonNull(im.getLore()).stream()
                    .map(s -> ChatUtil.fixColor(this.replaceFromMap(s, map))).collect(Collectors.toList()));
        }

        is.setItemMeta(im);
        return is;
    }


    public ItemStack setAndFixColorsWithReplace(List<String> lore, Map<String, String> map) {
        ItemStack is = new ItemStack(this.is);
        ItemMeta im = is.getItemMeta();
        assert im != null;

        if(im.hasDisplayName()) {
            im.setDisplayName(ChatUtil.fixColor(this.replaceFromMap(im.getDisplayName(), map)));
        }

        im.setLore(Objects.requireNonNull(lore).stream()
                .map(s -> ChatUtil.fixColor(this.replaceFromMap(s, map))).collect(Collectors.toList()));

        is.setItemMeta(im);
        return is;
    }

    public ItemStack addAndFixColorsWithReplace(List<String> lore, Map<String, String> map) {
        ItemStack is = new ItemStack(this.is);
        ItemMeta im = is.getItemMeta();
        assert im != null;

        if(im.hasDisplayName()) {
            im.setDisplayName(ChatUtil.fixColor(this.replaceFromMap(im.getDisplayName(), map)));
        }

        List<String> stringList = new ArrayList<>();

        if(im.hasLore()) {
            stringList.addAll(Objects.requireNonNull(im.getLore()).stream()
                    .map(s -> ChatUtil.fixColor(this.replaceFromMap(s, map))).collect(Collectors.toList()));
        }
        stringList.addAll(Objects.requireNonNull(lore).stream()
                .map(s -> ChatUtil.fixColor(this.replaceFromMap(s, map))).collect(Collectors.toList()));

        im.setLore(stringList);
        is.setItemMeta(im);
        return is;
    }

    public ItemStack addAndFixColors(List<String> lore) {
        ItemStack is = new ItemStack(this.is);
        ItemMeta im = is.getItemMeta();
        assert im != null;

        if(im.hasDisplayName()) {
            im.setDisplayName(ChatUtil.fixColor(im.getDisplayName()));
        }

        List<String> stringList = new ArrayList<>();

        if(im.hasLore()) {
            stringList.addAll(Objects.requireNonNull(im.getLore()).stream()
                    .map(ChatUtil::fixColor).collect(Collectors.toList()));
        }
        stringList.addAll(Objects.requireNonNull(lore).stream()
                .map(ChatUtil::fixColor).collect(Collectors.toList()));

        im.setLore(stringList);
        is.setItemMeta(im);
        return is;
    }

    public ItemStack setAndFixColorsWithReplace(String name, List<String> lore, Map<String, String> map) {
        ItemStack is = new ItemStack(this.is);
        ItemMeta im = is.getItemMeta();
        assert im != null;

        im.setDisplayName(ChatUtil.fixColor(this.replaceFromMap(name, map)));

        im.setLore(Objects.requireNonNull(lore).stream()
                .map(s -> ChatUtil.fixColor(this.replaceFromMap(s, map))).collect(Collectors.toList()));

        is.setItemMeta(im);
        return is;
    }

    private String replaceFromMap(final String text, Map<String, String> map) {
        return map.entrySet().stream()
                .map(entryToReplace -> (Function<String, String>) s ->
                        s.replace(entryToReplace.getKey(), entryToReplace.getValue()))
                                .reduce(Function.identity(), Function::andThen)
                                .apply(text);
    }
}
