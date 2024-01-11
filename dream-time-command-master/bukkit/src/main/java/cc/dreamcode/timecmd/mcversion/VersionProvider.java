package cc.dreamcode.timecmd.mcversion;

import cc.dreamcode.timecmd.mcversion.api.VersionAccessor;
import cc.dreamcode.timecmd.mcversion.v1_13_R1.V1_13_R1_VersionAccessor;
import cc.dreamcode.timecmd.mcversion.v1_8_R3.V1_8_R3_VersionAccessor;
import com.cryptomorin.xseries.ReflectionUtils;

public class VersionProvider {
    public static VersionAccessor getVersionAccessor() {
        switch (ReflectionUtils.NMS_VERSION) {
            case "v1_8_R1":
            case "v1_8_R2":
            case "v1_8_R3":
            case "v1_9_R1":
            case "v1_9_R2":
            case "v1_10_R1":
            case "v1_11_R1":
            case "v1_12_R1": {
                return new V1_8_R3_VersionAccessor();
            }
            case "v1_13_R1":
            case "v1_13_R2":
            case "v1_14_R1":
            case "v1_14_4_R1":
            case "v1_15_R1":
            case "v1_16_R1":
            case "v1_16_R2":
            case "v1_16_R3":
            case "v1_17_R1":
            case "v1_17_1_R1":
            case "v1_18_R1":
            case "v1_18_R2":
            case "v1_19_R1":
            case "v1_19_R2":
            case "v1_19_R3":
            case "v1_19_1_R1":
            case "v1_20_R1": {
                return new V1_13_R1_VersionAccessor();
            }
            default: {
                throw new RuntimeException("Plugin doesn't support this server version, update to: 1.8.8, 1.12.2, 1.16.5, 1.17.1, 1.18.2 or 1.19.3.");
            }
        }
    }
}
