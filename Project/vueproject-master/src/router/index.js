// 导入组件
import Vue from 'vue';
import Router from 'vue-router';

//注册
import zhuce from '@/views/zhuce';

// 登录
import login from '@/views/login';
// 首页
import index from '@/views/index';

import yuyu from '@/views/system/yuyu'

import xx from '@/views/system/xx'
/**
 * 基础菜单
 */
// 商品管理
import Goods from '@/views/goods/Goods';
// 机器信息管理
import Machine from '@/views/machine/Machine';
// 货道信息管理
import MachineAisle from '@/views/machine/MachineAisle';
/**
 * 订单管理
 */
// 交易订单
import Order from '@/views/pay/Order';
/**
 * 系统管理
 */
// 用户管理
import user from '@/views/system/user';
// 菜单管理
import Module from '@/views/system/Module';
// 药品管理
import Role from '@/views/system/Role';
// 疾病管理
import Dept from '@/views/system/Dept';
// 系统环境变量
import Variable from '@/views/system/Variable';
// 权限管理
import Permission from '@/views/system/Permission';
/**
 * 支付管理
 */
// 支付配置信息
import MachineConfig from '@/views/machine/MachineConfig';
// 支付配置
import Config from '@/views/pay/Config';
/**
 * 数据监控
 */
// 监控查询
import druidLogin from '@/views/druid/login';

// 图表界面
import statistics from '@/views/charts/statistics';
import a from '@/views/charts/a';
import b from '@/views/charts/b';
import c from '@/views/charts/c';
import d from '@/views/charts/d';

// 启用路由
Vue.use(Router);

// 导出路由 
export default new Router({
    routes: [{
        path: '/',
        name: '',
        component: login,
        hidden: true,
        meta: {
            requireAuth: false
        }
    }, {
        path: '/zhuce',
        name: 'Register',
        component: zhuce,
        hidden: true,
        meta: {
            requireAuth: false
        }
    }, 
    {
        path: '/login',
        name: 'Login',
        component: login,
        hidden: true,
        meta: {
            requireAuth: false
        }
    }, {
        path: '/index',
        name: 'Front Page',
        component: index,
        iconCls: 'el-icon-tickets',
        children: [{
            path: '/goods/Goods',
            name: 'Product Management',
            component: Goods,
            meta: {
                requireAuth: true
            }
        }, {
            path: '/machine/Machine',
            name: 'Machine Information Management',
            component: Machine,
            meta: {
                requireAuth: true
            }
        }, {
            path: '/machine/MachineAisle',
            name: 'Cargo channel information management',
            component: MachineAisle,
            meta: {
                requireAuth: true
            }
        }, {
            path: '/pay/Order',
            name: 'Trading Orders',
            component: Order,
            meta: {
                requireAuth: true
            }
        }, {
            path: '/system/user',
            name: 'User Management',
            component: user,
            meta: {
                requireAuth: true
            }
        }, {
            path: '/system/Module',
            name: 'Menu Management',
            component: Module,
            meta: {
                requireAuth: true
            }
        }, {
            path: '/system/Role',
            name: 'Role Management',
            component: Role,
            meta: {
                requireAuth: true
            }
        }, 
        {
            path: '/charts/a',
            name: 'Amount Statistics',
            component: a,
            meta: {
                requireAuth: true
            }
        }, {
            path: '/charts/b',
            name: 'Amount Statistics',
            component: b,
            meta: {
                requireAuth: true
            }
        }, {
            path: '/charts/c',
            name: 'Amount Statistics',
            component: c,
            meta: {
                requireAuth: true
            }
        }, {
            path: '/charts/d',
            name: 'Amount Statistics',
            component: d,
            meta: {
                requireAuth: true
            }
        }, 
        {
            path: '/system/yuyu',
            name: 'Make an appointment with a doctor',
            component: yuyu,
            meta: {
                requireAuth: true
            }
        },  {
            path: '/system/xx',
            name: 'Personal Information',
            component: xx,
            meta: {
                requireAuth: true
            }
        }, {
            path: '/system/Dept',
            name: 'Company Management',
            component: Dept,
            meta: {
                requireAuth: true
            }
        }, {
            path: '/system/Variable',
            name: 'System environment variables',
            component: Variable,
            meta: {
                requireAuth: true
            }
        }, {
            path: '/system/Permission',
            name: 'Permission Management',
            component: Permission,
            meta: {
                requireAuth: true
            }
        }, {
            path: '/machine/MachineConfig',
            name: 'Payment configuration information',
            component: MachineConfig,
            meta: {
                requireAuth: true
            }
        }, {
            path: '/pay/Config',
            name: 'Payment Configuration',
            component: Config,
            meta: {
                requireAuth: true
            }
        }, {
            path: '/druid/login',
            name: 'Monitoring Query',
            component: druidLogin,
            meta: {
                requireAuth: true
            }
        }, {
            path: '/charts/statistics',
            name: 'Data Visualization',
            component: statistics,
            meta: {
                requireAuth: true
            }
        }]
    }]
})