import { Moment } from 'moment';
import { IProcessMsgTaskMpPi } from 'app/shared/model/process-msg-task-mp-pi.model';
import { DdMessageType } from 'app/shared/model/enumerations/dd-message-type.model';

export interface IDdMessageMpPi {
  id?: number;
  receivingDepartment?: string;
  receivingUser?: string;
  title?: string;
  json?: string;
  sendTime?: Moment;
  type?: DdMessageType;
  processMsgTask?: IProcessMsgTaskMpPi;
}

export class DdMessageMpPi implements IDdMessageMpPi {
  constructor(
    public id?: number,
    public receivingDepartment?: string,
    public receivingUser?: string,
    public title?: string,
    public json?: string,
    public sendTime?: Moment,
    public type?: DdMessageType,
    public processMsgTask?: IProcessMsgTaskMpPi
  ) {}
}
