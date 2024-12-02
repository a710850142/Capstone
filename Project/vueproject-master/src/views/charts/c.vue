/**
* 图表界面
*/ 
<template>
  <!-- 组件主盒子 -->
  <div class="stbox">
    <!-- 面包屑导航 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/' }">Front Page</el-breadcrumb-item>
      <el-breadcrumb-item>Total number of orders</el-breadcrumb-item>
    </el-breadcrumb>
    <!-- 搜索，切换 -->
   
    <!-- 统计图 -->
    <el-row :gutter="46">
      <el-col :span="23" class="text-c">
        <div class="paybox">
            <div class="cavasbox" ref="payNumEchart"></div>
          </div>
      </el-col>
      <!-- <el-col :span="8" class="text-c">
        <div class="st-gbox">
          <div class="cavasbox" ref="SUMEchart"></div>
        </div>
      </el-col>
      <el-col :span="8" class="text-c">
        <div class="st-gbox">
          <div class="cavasbox" ref="ClickEchart"></div>
        </div>
      </el-col> -->
    </el-row>
    <!-- 统计图 -->
    
  </div>
</template>
<script type="text/ecmascript-6">
import Chart from 'echarts'
import { tj } from '../../api/payMG'

export default {
  name: "welcome",
  data() {
    return {
      machineNo: '',
      type: 'day',
      //  销售总笔数 
      SCEoption: {
        tooltip: {
          trigger: 'item',
          formatter: "{a} <br/>{b}Month : {c}"
        },
        legend: {
          data: [{
            name: 'Total number of orders',
            icon: 'rect'
          }],
          top: 1,
          left: 1,
          itemGap: 10,
          itemWidth: 12,
          itemHeight: 12,
          textStyle: {
            fontSize: 12,
            color: "#323232"
          }
        },
        grid: {
          left: 50,
          right: 10,
          top: 30,
          bottom: 30,
          borderWidth: 1
        },
        xAxis: {
          type: 'category',
          data: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
          axisLine: {
            lineStyle: {
              color: "#999999",
              width: 1
            }
          },
          axisLabel: {
            margin: 14,
            height: 70,
            interval: 0,
            textStyle: {
              fontSize: 10,
              color: "#999999"
            }
          }
        },
        yAxis: {
          type: 'value',
          axisLine: {
            lineStyle: {
              color: "#999999",
              width: 1
            }
          },
          axisLabel: {
            margin: 14,
            textStyle: {
              fontSize: 10,
              color: "#999999"
            }
          }
        },
        series: [{
          name: 'Total number of orders',
          type: 'bar',
          barGap: 0,
          data: [1],
          barWidth: 10,
          itemStyle: {
            normal: {
              color: new Chart.graphic.LinearGradient(
                0, 0, 0, 1,
                [
                  { offset: 0, color: '#83bff6' },
                  { offset: 0.5, color: '#188df0' },
                  { offset: 1, color: '#188df0' }
                ]
              )
            },
            emphasis: {
              color: new Chart.graphic.LinearGradient(
                0, 0, 0, 1,
                [
                  { offset: 0, color: '#2378f7' },
                  { offset: 0.7, color: '#2378f7' },
                  { offset: 1, color: '#83bff6' }
                ]
              )
            }
          }
        }]
      },
      //  销售总金额 
      SUMoption: {
        tooltip: {
          trigger: 'item',
          formatter: "{a} <br/>{b}Month : {c}"
        },
        legend: {
          data: [{
            name: 'Total Amount',
            icon: 'rect'
          }],
          top: 1,
          left: 1,
          itemGap: 10,
          itemWidth: 12,
          itemHeight: 12,
          textStyle: {
            fontSize: 12,
            color: "#323232"
          }
        },
        grid: {
          left: 50,
          right: 10,
          top: 30,
          bottom: 30,
          borderWidth: 1
        },
        xAxis: {
          type: 'category',
          data: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
          axisLine: {
            lineStyle: {
              color: "#999999",
              width: 1
            }
          },
          axisLabel: {
            margin: 14,
            height: 70,
            interval: 0,
            textStyle: {
              fontSize: 10,
              color: "#999999"
            }
          }
        },
        yAxis: {
          type: 'value',
          axisLine: {
            lineStyle: {
              color: "#999999",
              width: 1
            }
          },
          axisLabel: {
            margin: 14,
            textStyle: {
              fontSize: 10,
              color: "#999999"
            }
          }
        },
        series: [{
          name: 'Total Amount',
          //   type: 'bar',
          type: 'line',
          barGap: 0,
          data: [],
          barWidth: 10,
          itemStyle: {
            color: "#108ff9"
          }
        }]
      },
      //  总点击量
      Clickoption: {
        tooltip: {
          trigger: 'item',
          formatter: "{a} <br/>{b}Month : {c}"
        },
        legend: {
          data: [{
            name: 'Statistics of medication usage',
            icon: 'rect'
          }],
          top: 1,
          left: 1,
          itemGap: 10,
          itemWidth: 12,
          itemHeight: 12,
          textStyle: {
            fontSize: 12,
            color: "#323232"
          }
        },
        grid: {
          left: 50,
          right: 10,
          top: 30,
          bottom: 30,
          borderWidth: 1
        },
        xAxis: {
          type: 'category',
          data: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12'],
          axisLine: {
            lineStyle: {
              color: "#999999",
              width: 1
            }
          },
          axisLabel: {
            margin: 14,
            height: 70,
            interval: 0,
            textStyle: {
              fontSize: 10,
              color: "#999999"
            }
          }
        },
        yAxis: {
          type: 'value',
          axisLine: {
            lineStyle: {
              color: "#999999",
              width: 1
            }
          },
          axisLabel: {
            margin: 14,
            textStyle: {
              fontSize: 10,
              color: "#999999"
            }
          }
        },
        series: [{
          name: 'Statistics of medication usage',
          type: 'bar',
          barGap: 0,
          data: [],
          barWidth: 10,
          itemStyle: {
            color: "#48cefd"
          }
        }]
      },
      //  
      payoption: {
        backgroundColor: '#2c343c',
        title: {
          text: 'Statistics by pet category',
          left: 10,
          top: 5,
          textStyle: {
            fontSize: 12,
            color: '#ccc'
          }
        },

        tooltip: {
          trigger: 'item',
          formatter: "{a} <br/>{b} : {c} ({d}%)"
        },

        visualMap: {
          show: false,
          min: 1,
          max: 20,
          inRange: {
            colorLightness: [0, 1]
          }
        },
        series: [
          {
            name: 'Statistics by pet category',
            type: 'pie',
            radius: '55%',
            center: ['50%', '50%'],
            data: [].sort(function (a, b) { return a.value - b.value; }),
            roseType: 'radius',
            label: {
              normal: {
                textStyle: {
                  color: 'rgba(255, 255, 255, 0.3)'
                }
              }
            },
            labelLine: {
              normal: {
                lineStyle: {
                  color: 'rgba(255, 255, 255, 0.3)'
                },
                smooth: 0.2,
                length: 10,
                length2: 20
              }
            },
            itemStyle: {
              normal: {
                color: '#c23531',
                shadowBlur: 200,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            },

            animationType: 'scale',
            animationEasing: 'elasticOut',
            animationDelay: function (idx) {
              return Math.random() * 200;
            }
          }
        ]
      },
      payNumoption: {
        backgroundColor: '#2c343c',
        title: {
          text: 'Statistics of diseases',
          left: 10,
          top: 5,
          textStyle: {
            fontSize: 12,
            color: '#ccc'
          }
        },

        tooltip: {
          trigger: 'item',
          formatter: "{a} <br/>{b} : {c} ({d}%)"
        },

        visualMap: {
          show: false,
          min: 10,
          max: 200,
          inRange: {
            colorLightness: [0, 1]
          }
        },
        series: [
          {
            name: 'Statistics of diseases',
            type: 'pie',
            radius: '55%',
            center: ['50%', '50%'],
            data: [
            ].sort(function (a, b) { return a.value - b.value; }),
            roseType: 'radius',
            label: {
              normal: {
                textStyle: {
                  color: 'rgba(255, 255, 255, 0.3)'
                }
              }
            },
            labelLine: {
              normal: {
                lineStyle: {
                  color: 'rgba(255, 255, 255, 0.3)'
                },
                smooth: 0.2,
                length: 10,
                length2: 20
              }
            },
            itemStyle: {
              normal: {
                color: '#c23531',
                shadowBlur: 200,
                shadowColor: 'rgba(0, 0, 0, 0.5)'
              }
            },

            animationType: 'scale',
            animationEasing: 'elasticOut',
            animationDelay: function (idx) {
              return Math.random() * 200;
            }
          }
        ]
      },
    }
  },
  // 导入组件
  components: {
    // 点聚合组件
  },
  // 创建完毕状态(里面是操作)
  mounted() {
    
    
    this.getpayNum()

   },
  // 挂载结束状态(里面是操作)
  created() {
    
  },
  // 里面的函数只有调用才会执行
  methods: {

   


    // 交易总笔数
    getSCE() {

      tj()
        .then(res => {
         
          this.SCEoption.series[0].data=res.l1
          
          this.chart = Chart.init(this.$refs.SCEchart)
          this.chart.setOption(this.SCEoption)
          

        })

      
      
        
    },
    // 交易总金额
    getSUM() {

      tj()
        .then(res => {
        
          this.SUMoption.series[0].data=res.l2
        
          this.chart = Chart.init(this.$refs.SUMEchart)
          this.chart.setOption(this.SUMoption)

        })


      
    },
    // 
    getClick() {
      
      tj()
        .then(res => {
              
          this.Clickoption.series[0].data=res.l4
       
          this.chart = Chart.init(this.$refs.ClickEchart)
      this.chart.setOption(this.Clickoption)
        })


      
    },
    // 
    getpay() {

      tj()
        .then(res => {
          console.log(res);
          console.log(this.payoption.series[0].data);
         
          this.payoption.series[0].data=res.l3
          console.log(this.payoption.series[0].data);
          console.log("12312312344");
          console.log(this.payoption.series[0].itemStyle.normal.color);

          this.chart = Chart.init(this.$refs.payEchart)
          this.chart.setOption(this.payoption)
        })
        
      

     
    },
    // 
    getpayNum() {

      tj()
        .then(res => {
         
          console.log(res);
          console.log(this.payNumoption.series[0].data);
          this.payNumoption.series[0].data = res.l5
          console.log(this.payNumoption);
          this.payNumoption.series[0].itemStyle.normal.color='#c23531'
          this.chart = Chart.init(this.$refs.payNumEchart)
          this.chart.setOption(this.payNumoption)

        })

        


      
    }

  }
};
</script>
<style>
.stbox {
  width: 100%;
  height: 100%;
  box-sizing: border-box;
}
.stbgc {
  background-color: #fff;
  height: 60px;
  line-height: 60px;
  border-radius: 5px;
  padding: 0px 16px;
}
.stsearch {
  text-align: center;
}
.text-c {
  opacity: 0.95;
  text-align: center;
}
.st-gbox {
  background-color: #fff;
  margin-top: 20px;
  border-radius: 5px;
  height: 0vh; 
  box-sizing: border-box;
  padding: 10px;
}
.cavasbox {
  box-sizing: border-box;
  width: 100%;
  height: 100%;
}
.paybox {
  width: 100%;
  background-color: #fff;
  box-sizing: border-box;
  border-radius: 5px;
  margin-top: 20px;
  height: 80vh;
}
</style>