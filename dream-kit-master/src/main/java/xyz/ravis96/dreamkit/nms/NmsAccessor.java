package xyz.ravis96.dreamkit.nms;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.ravis96.dreamkit.nms.notice.NoticeAccessor;

@Getter
@RequiredArgsConstructor
public class NmsAccessor {

    private final NoticeAccessor noticeAccessor = new NoticeAccessor();

}
