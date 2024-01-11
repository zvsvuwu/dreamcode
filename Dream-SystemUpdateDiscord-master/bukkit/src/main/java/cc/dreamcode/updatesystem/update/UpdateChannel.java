package cc.dreamcode.updatesystem.update;

import com.cryptomorin.xseries.XMaterial;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateChannel {

    private XMaterial material;
    private String categoryName;
    private String categoryDisplay;
}
