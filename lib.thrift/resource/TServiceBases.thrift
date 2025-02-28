namespace java com.demoproject.thrift.tcommon

enum ServiceStatus {
    STARTED = 0,
    STOPPED = 1,
    SHUTTING_DOWN = 2,
    MAINTAIN = 3
}

enum EStatusResult {
    OK = 0,
    FAIL = 1
}

struct TResponseInfo {
    1: list<string> playerIdReceive,
    2: i32 length,
    3: binary messageData
}

service ServiceBases {
    bool ping(),
    TResponseInfo pushMessage(1:string token, 2:i32 moduleId, 3:string playerId, 4:i32 msgType, 5:i32 length, 6:binary msgData)
}
