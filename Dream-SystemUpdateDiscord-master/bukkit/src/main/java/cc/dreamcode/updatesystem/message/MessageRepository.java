package cc.dreamcode.updatesystem.message;

import eu.okaeri.persistence.repository.DocumentRepository;
import eu.okaeri.persistence.repository.annotation.DocumentCollection;

@DocumentCollection(path = "messages", keyLength = 24)
public interface MessageRepository extends DocumentRepository<String, Message> {

}
