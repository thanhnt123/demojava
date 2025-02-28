include "TServiceBases.thrift"

namespace java com.demoproject.thrift.account

enum EJobType { 
    CREATE,
    UPDATE,
    DELETE
}

enum EStatus {
    ENABLE = 1,
    DISABLE = 2,
    DELETE = 3
}

struct TAccount {
    1: i32 id,
    2: string username,
    3: string displayname,
    4: string password,
    5: string status,
    6: string avatarURL,
    7: string note,
    8: string address,
    9: string phone,
    10: string platform,
    11: i64 lastedLogin,
    12: i64 verifiedAt,
    13: string createdAt,
    14: string updatedAt,
}

struct TAccountResult {
    1: TServiceBases.EStatusResult status = EStatusResult.OK,
    2: TAccount value,
}

struct TAccountListResult {
    1: TServiceBases.EStatusResult status = EStatusResult.OK,
    2: list<TAccount> listData,
    4: i64 totalRecords
}

service AccountService extends TServiceBases.ServiceBases {
    bool create(1:TAccount value),
    bool update(1:TAccount value),
    bool remove(1:i32 id),
    TAccountResult getById(1:string id),
    TAccountResult getByName(1:string username),
    TAccountListResult getListkeys(1:list<string> listKeys),
    TAccountListResult getAll(),
    TAccountListResult getMulti(1:string whereClause, 2:i32 offset, 3:i32 count),
    bool checkExistUserName(1: string userName),
    bool checkExistEmail(1: string email)
}