<template>
    <div v-if="audits">
        <h2 id="audits-page-heading">Audits</h2>

        <div class="row">
            <div class="col-md-5">
                <h4>Filter by date</h4>
                <div class="input-group mb-3">
                    <div class="input-group-prepend">
                        <span class="input-group-text">from</span>
                    </div>
                    <input type="date" class="form-control" name="start" v-model="fromDate" v-on:change="transition()" required/>

                    <div class="input-group-append">
                        <span class="input-group-text">To</span>
                    </div>
                    <input type="date" class="form-control" name="end" v-model="toDate" v-on:change="transition()" required/>
                </div>
            </div>
        </div>

        <div class="table-responsive">
            <table class="table table-sm table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('auditEventDate', 'timestamp')"><span>Date</span><font-awesome-icon v-if="propOrder === 'auditEventDate'" icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('principal', 'principal')"><span>User</span><font-awesome-icon v-if="propOrder === 'principal'" icon="sort"></font-awesome-icon></th>
                    <th v-on:click="changeOrder('auditEventType', 'type')"><span>State</span><font-awesome-icon v-if="propOrder === 'auditEventType'" icon="sort"></font-awesome-icon></th>
                    <th><span>Extra data</span></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="audit of orderBy(audits, predicate, reverse === true ? 1 : -1)" :key="audit.timestamp">
                    <td><span>{{audit.timestamp | formatDate}}</span></td>
                    <td><small>{{audit.principal}}</small></td>
                    <td>{{audit.type}}</td>
                    <td>
                        <span v-if="audit.data && audit.data.message">{{audit.data.message}}</span>
                        <span v-if="audit.data && audit.data.remoteAddress"><span>Remote Address</span> {{audit.data.remoteAddress}}</span>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div>
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="totalItems" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./audits.component.ts">
</script>
