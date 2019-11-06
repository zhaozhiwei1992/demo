import { createLocalVue, shallowMount, Wrapper } from '@vue/test-utils';
import Ribbon from '@/core/ribbon/ribbon.vue';
import RibbonClass from '@/core/ribbon/ribbon.component';

import * as config from '@/shared/config/config';

const localVue = createLocalVue();
config.initVueApp(localVue);
const store = config.initVueXStore(localVue);

describe('Ribbon', () => {
  let ribbon: RibbonClass;
  let wrapper: Wrapper<RibbonClass>;

  const wrap = async (managementInfo?: any) => {
    wrapper = shallowMount<RibbonClass>(Ribbon, {
      store,
      localVue
    });
    ribbon = wrapper.vm;
    await ribbon.$nextTick();
  };

  beforeEach(() => {
    store.commit('setRibbonOnProfiles', null);
  });

  it('should be a Vue instance', async () => {
    await wrap();
    expect(wrapper.isVueInstance()).toBeTruthy();
  });

  it('should not have ribbonEnv when no data', async () => {
    await wrap();
    expect(ribbon.ribbonEnv).toBeNull();
  });

  it('should have ribbonEnv set to value in store', async () => {
    const profile = 'dev';
    store.commit('setRibbonOnProfiles', profile);
    expect(ribbon.ribbonEnv).toEqual(profile);
  });
});
