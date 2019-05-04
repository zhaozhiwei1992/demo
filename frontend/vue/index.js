// https://cn.vuejs.org/v2/guide/
var app = new Vue({
  el: '#app',
  data: {
    message: 'Hello world!'
  }
});

var app2 = new Vue({
  el: '#app-2',
  data: {
    message: '页面加载于 ' + new Date().toLocaleString()
  }
});

var app3 = new Vue({
  el: "#app-3",
  data: {
    seen: true
  }
});

//  for 循环
var app4 = new Vue({
  el: "#app-4",
  data: {
    todos: [{
        text: 'text1'
      },
      {
        text: 'text2'
      },
      {
        text: 'text3'
      }
    ],
    object: {
      id: 1,
      name: 'zhangsan',
      age: 18
    }
  }
});

var app5 = new Vue({
  el: '#app-5',
  data: {
    message: 'ababab'
  },
  methods: {
    reverseMessage: function () {
      // this.message="babab";
      this.message = this.message.split('').reverse().join('');
    }
  }
});

//  数据双向绑定
var app6 = new Vue({
  el: '#app-6',
  data: {
    message: 'default value'
  }
});

//  组件开始
Vue.component(
  'todo-item', {
    template: '<li>待办1</li>'
  }
)
var app7 = new Vue({
  el: '#app-7'
})
// 组件结束， 后面这个new的部分必须有，才会绑定

//我们应该能从父作用域将数据传到子组件才对。让我们来修改一下组件的定义，使之能够接受一个 prop：
Vue.component(
  'todo-item', {
    props: ['todo'],
    template: '<li>{{todo.text}}</li>'
  }
);

var app8 = new Vue({
  el: '#app-8',
  data: {
    groceryList: [{
        id: 0,
        text: '蔬菜'
      },
      {
        id: 1,
        text: '奶酪'
      },
      {
        id: 2,
        text: '随便其它什么人吃的东西'
      }
    ]
  }
});

// 注意的是只有当实例被创建时 data 中存在的属性才是响应式的。也就是说如果你添加一个新的属性，比如：
// vm.b = 'hi'
// 那么对 b 的改动将不会触发任何视图的更新。如果你知道你会在晚些时候需要一个属性，但是一开始它为空或不存在，那么你仅需要设置一些初始值。比如：
var data = {
  a: 1
};
var app9 = new Vue({
  el: '#app-9',
  data: data
});
data.b = 1
// 不会触发视图更新

//阻止viewmodel绑定
var obj = {
  foo: 'bar'
};
// 阻止关键逻辑
Object.freeze(obj);

var app10 = new Vue({
  el: '#app-10',
  data: obj
});

//监控变更
var obj = {
  a: 1
};

var app11 = new Vue({
  el: '#app-11',
  data: obj,
  //  比如 created 钩子可以用来在一个实例被创建之后执行代码：
  created() {
    console.log("a is " + this.a);
  }

});

console.log(app11.$data === data); //true
console.log(app11.$el = document.getElementById('#app-11'));

app11.$watch('a', function (newValue, oldValue) {
  console.log("新值: " + newValue + " 原值: " + oldValue); //新值: 66 原值: 1
});
obj.a = 66;

// 三目运算, 必须打开vue环境mustache才会生效
var app11 = new Vue({
  el: '#app-11'
});

//computed
var app12 = new Vue({
  el: '#app-12',
  data: {
    message: 'ababab'
  },
  //app12.reversedMessage 这种方式下，缓存，如果不改变message的值，不会再去执行reversedMessage
  computed: {
    reversedMessage() {
      console.log("computed --> reverMessage");
      return this.message.split("").reverse().join("");
    }
  },
  methods: {
    // 使用方法的形式， 每次都会执行, 这里名字不能使用同一个，否则识别其中一个
    reverseMessage: function () {
      console.log("methods --> reverseMessage");
      return this.message.split("").reverse().join("");
    }
  }
});

// 侦听器
var watchExampleVM = new Vue({
  el: '#watch-example',
  data: {
    question: '',
    answer: 'I cannot give you an answer until you ask a question!'
  },
  watch: {
    // 如果 `question` 发生改变，这个函数就会运行
    question: function (newQuestion, oldQuestion) {
      this.answer = 'Waiting for you to stop typing...'
      this.debouncedGetAnswer()
    }
  },
  created: function () {
    // `_.debounce` 是一个通过 Lodash 限制操作频率的函数。
    // 在这个例子中，我们希望限制访问 yesno.wtf/api 的频率
    // AJAX 请求直到用户输入完毕才会发出。想要了解更多关于
    // `_.debounce` 函数 (及其近亲 `_.throttle`) 的知识，
    // 请参考：https://lodash.com/docs#debounce
    this.debouncedGetAnswer = _.debounce(this.getAnswer, 500)
  },
  methods: {
    getAnswer: function () {
      // 不带？叫问题?
      if (this.question.indexOf('?') === -1) {
        this.answer = 'Questions usually contain a question mark. ;-)'
        return
      }
      this.answer = 'Thinking...'
      var vm = this
      axios.get('https://yesno.wtf/api')
        .then(function (response) {
          vm.answer = _.capitalize(response.data.answer)
        })
        .catch(function (error) {
          vm.answer = 'Error! Could not reach the API. ' + error
        })
    }
  }
});

var app13 = new Vue({
  el: '#app-13',
  data: {
    isActive: true,
    error: null
  },
  computed: {
    classObject() {
      return {
        active: this.isActive && !this.error,
        'text-danger': this.error && this.error.type === 'fatal'
      }
    }
  }
});

var app14 = new Vue({
  el: '#app-14',
  data: {
    isActive: true,
    activeClass: 'active',
    errorClass: 'text-danger'
  }
});

var app15 = new Vue({
  el: '#app-15',
  data: {
    activeColor: 'red',
    fontSize: 15,
    styleObject: {
      color: 'yellow',
      fontSize: 15,
    }

  }
});

var app16 = new Vue({
  el: '#app-16',
  data: {
    type: 'B'
  }
});

var app17 = new Vue({
  el: '#app-17',
  data: {
    loginType: 'username'
  }
});

var app18 = new Vue({
  el: '#app-18'
});

// 增加书籍
Vue.component('book-item', {
  template: '\
    <li>\
      {{ name }}\
      <button v-on:click="$emit(\'remove\')">Remove</button>\
    </li>\
  ',
  props: ['name']
});

//todo list
var app19 = new Vue({
  el: '#app-19',
  data: {
    books: [{
        id: 1,
        name: "math"
      },
      {
        id: 2,
        name: "computer"
      },
      {
        id: 3,
        name: "english"
      }
    ],
    nextBookId: 4,
    bookname: ''
  },
  methods: {
    addNewBook: function () {
      this.books.push({
        id: this.nextBookId++,
        name: this.bookname
      });
      bookname = '';
    }
  }
});

var app20 = new Vue({
  el: '#app-20',
  methods: {
    say(message) {
      alert(message);
    }
  }
});

var app21 = new Vue({
  el: '#app-21',
  data: {
    message: ''
  }
});

var app22 = new Vue({
  el: '#app-22',
  data: {
    checked: false
  }
});

// 定义一个名为 button-counter 的新组件
// data 必须是一个函数, 所以才可以才有自己的遍历范围，如果是data报错
Vue.component('button-counter', {
  data: function () {
    return {
      count: 0
    }
  },
  template: '<button v-on:click="count++">You clicked me {{ count }} times.</button>'
});

var app23 = new Vue({
  el: '#app-23'
});

// 全局组件
Vue.component('component-a', { /* ... */ 
    data () {
      return {
        count:0
      }
    },
    template:'<h1>我是h1</h1> <component-c></component-c>'
})
Vue.component('component-b', { /* ... */
    data () {
      return {
        count:0
      }
    },
    template:'<h2>我是h2</h2>'
 })
Vue.component('component-c', { /* ... */ 
    data () {
      return {
        count:0
      }
    },
    template:'<h3>我是h3</h3>'
})

var app24 = new Vue({
  el:'#app-24'
})

var Component1 = {
    template:'<h2>局部1</h2>'
}

var Component2 = {
    template:'<h2>局部2</h2>'
}

var Component3 = {
    template:'<h2>局部3</h2>'
}

var app25 = new Vue({
  el:'#app-25',
  components: {
    'component-1': Component1,
    'component-2': Component2
  } 
})

//  props
var ComponentMyName={
   props: ['hh'],
    template:'<h2>局部3{{hh}}</h2>'
}
var app26 = new Vue({
  el:'#app-26',
  components:{
    'name-show': ComponentMyName
  }
})

/**
 *  这里自定义组件中model表示:
 *  1. v-model绑定的属性会跟checked进行绑定
 *  2. 事件源编程了change, 如果去掉v-on:change部分，勾选checkbox不会刷新lovingVue的值
 *  3. 自定义model双向绑定时推荐sync的方式
 */
Vue.component('base-checkbox', {
  model: {
    prop: 'checked',
    event: 'change'
  },
  props: {
    checked: Boolean
  },
  template: `
    <input
      type="checkbox"
      v-bind:checked.sync="checked"
      v-on:change="$emit('change', $event.target.checked)"
    >
  `
})

var app27=new Vue({
  el:'#app-27',
  data:{
    lovingVue:true
  }
})

//自定义input
Vue.component('base-input', {
  inheritAttrs: false,
  props: ['label', 'value'],
  computed: {
    inputListeners: function () {
      var vm = this
      // `Object.assign` 将所有的对象合并为一个新对象
      return Object.assign({},
        // 我们从父级添加所有的监听器
        this.$listeners,
        // 然后我们添加自定义监听器，
        // 或覆写一些监听器的行为
        {
          // 这里确保组件配合 `v-model` 的工作
          input: function (event) {
            vm.$emit('input', event.target.value)
            // alert('输入中--', event);
          }
        }
      )
    }
  },
  template: `
    <label>
      {{ label }}
      <input
        v-bind="$attrs"
        v-bind:value="value"
        v-on="inputListeners"
      >
    </label>
  `
})

//创建 Vue 实例之前全局定义过滤器：{{ message | capitalize }}
Vue.filter('capitalize', function (value) {
  if (!value) return ''
  value = value.toString()
  return value.charAt(0).toUpperCase() + value.slice(1)
})

var app28=new Vue({
  el:'#app-28',
  data:{
    value:'xx',
    message:'hh'
  }
})

//插槽
Vue.component('base-layout',{
  template:`<div class="container">
  <header>  <slot name="header"></slot> </header>
  <main>
    <slot></slot>
  </main>
  <footer>
    <slot name="footer"></slot>
  </footer>
</div>`
})

var app29 = new Vue({
  el:'#app-29' 
})

Vue.component('todo-list',{
  template:`<ul>
  <li
    v-for="todo in filteredTodos"
    v-bind:key="todo.id"
  >
    <!--
    我们为每个 todo 准备了一个插槽，
    将 'todo' 对象作为一个插槽的 prop 传入。
    -->
    <slot name="todo" v-bind:todo="todo">
      <!-- 后备内容 -->
      {{ todo.text }}
    </slot>
  </li>
</ul>`
})

var app30=new Vue({
  el:'#app-30',
  data:{
    filteredTodos:[
      {id:'1', text:'zhangsan'},
      {id:'2', text:'lisi'},
      {id:'3', text:'wangwu'}
    ],
    todos:[
      {id:'1', text:'zhangsanxx'},
      {id:'2', text:'lisixx'},
      {id:'3', text:'wangwuxx'}
    ]
  }
})

// 注册一个全局自定义指令 `v-focus`
Vue.directive('focus', {
  // 当被绑定的元素插入到 DOM 中时……
  inserted: function (el) {
    // 聚焦元素
    el.focus()
  }
})

var app31=new Vue({
  el:'#app-31'
})

//  钩子函数参数
Vue.directive('demo', {
  bind: function (el, binding, vnode) {
    var s = JSON.stringify
    el.innerHTML =
      'name: '       + s(binding.name) + '<br>' +
      'value: '      + s(binding.value) + '<br>' +
      'expression: ' + s(binding.expression) + '<br>' +
      'argument: '   + s(binding.arg) + '<br>' +
      'modifiers: '  + s(binding.modifiers) + '<br>' +
      'vnode keys: ' + Object.keys(vnode).join(', ')
  }
})

var app32=new Vue({
  el: '#app-32',
  data: {
    message: 'hello!'
  }
})

// 动态调整
Vue.directive('tack', {
  bind(el, binding, vnode) {
    el.style.position = 'fixed';
    const s = (binding.arg == 'left' ? 'left' : 'top');
    el.style[s] = binding.value + 'px';
  }
})

// start app
new Vue({
  el: '#app-33',
  data() {
    return {
      dynamicleft: 500
    }
  }
})

Vue.directive('demo', function (el, binding) {
  console.log(binding.value.color) // => "white"
  console.log(binding.value.text)  // => "hello!"
})

var app34 = new Vue({
  el:'#app-34'
})

/**
 * <ul v-if="items.length">
  <li v-for="item in items">{{ item.name }}</li>
</ul>
<p v-else>No items found.</p>
 */
//  js替换原生v-for, v-if使用方式
var repforif = {
  props: ['items'],
  render: function (createElement) {
    if (this.items.length) {
      return createElement('ul', this.items.map(function (item) {
        return createElement('li', item.name)
      }))
    } else {
      return createElement('p', 'No items found.')
    }
 }
}

var app35=new Vue({
  el:'#app-35',
  data:{
    items:[
      {id:1, name:'zhangsan'},
      {id:2, name:'lisi'},
      {id:3, name:'wangwu'}
    ]
  },
  components:{
    'repforif': repforif
  }
})

// 标题 render
// Vue.component('anchored-heading', {
//   template: '#anchored-heading-template',
//   props: {
//     level: {
//       type: Number,
//       required: true
//     }
//   }
// })
Vue.component('anchored-heading', {
  render: function (createElement) {
    return createElement(
      'h' + this.level,   // 标签名称
      this.$slots.default // 子节点数组
    )
  },
  props: {
    level: {
      type: Number,
      required: true
    }
  }
})

var app36=new Vue({
  el:'#app-36'
})