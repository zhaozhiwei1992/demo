import axios from 'axios';
import Vue from 'vue';
import buildPaginationQueryOpts from '@/shared/sort/sorts';

export default class UserManagementService {
  public get(login): Promise<any> {
    return axios.get(`api/users/${login}`);
  }

  public create(user): Promise<any> {
    return axios.post('api/users', user);
  }

  public update(user): Promise<any> {
    return axios.put('api/users', user);
  }

  public remove(userId): Promise<any> {
    return axios.delete(`api/users/${userId}`);
  }

  public retrieve(req?: any): Promise<any> {
    return axios.get(`api/users?${buildPaginationQueryOpts(req)}`);
  }

  public retrieveAuthorities(): Promise<any> {
    return axios.get('api/users/authorities');
  }
}
