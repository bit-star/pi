import { Moment } from 'moment';
import { MessageStatus } from 'app/shared/model/enumerations/message-status.model';

export interface IProcessMsgTaskMpPi {
  id?: number;
  receivingDepartment?: string;
  receivingUser?: string;
  title?: string;
  json?: string;
  executeTime?: Moment;
  status?: MessageStatus;
}

export class ProcessMsgTaskMpPi implements IProcessMsgTaskMpPi {
  constructor(
    public id?: number,
    public receivingDepartment?: string,
    public receivingUser?: string,
    public title?: string,
    public json?: string,
    public executeTime?: Moment,
    public status?: MessageStatus
  ) {}
}
