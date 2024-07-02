// @Column(name = "user_name", length = 64)
// private String userName;

// @Column(name = "action_object", length = 32)
// @Enumerated(EnumType.STRING)
// private ActionObject actionObject;

// @Column(name = "action_name", length = 32)
// @Enumerated(EnumType.STRING)
// private ActionName actionName;

// @Column(name = "action_key", length = 64)
// private String actionKey;

// @Lob
// @Column(columnDefinition = "MEDIUMTEXT")
// private String description;

// @Column(length = 32)
// private String ip;

// @Column(name = "created")
// private Long created;

export interface UserLog {
  id?: number;
  userName?: string;
  actionObject?: string;
  actionName?: string;
  actionKey?: string;
  description?: string;
  ip?: string;
  created?: number;
}
