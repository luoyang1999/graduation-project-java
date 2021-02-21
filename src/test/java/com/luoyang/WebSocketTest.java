package com.luoyang;

import com.luoyang.websocket.WebSocketServer;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

@SpringBootTest
public class WebSocketTest {
    @Test
    public ResponseEntity<Void> testwebSocket() throws IOException{
        WebSocketServer.sendInfo("gggg","111");
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
