package xyz.ravis96.dreamfreeze.nms;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import xyz.ravis96.dreamfreeze.nms.notice.NoticeAccessor;

@Getter
@RequiredArgsConstructor
public class NmsAccessor {

    private final NoticeAccessor noticeAccessor = new NoticeAccessor();

}
