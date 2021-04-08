import React, {Component} from 'react';

import {AppState, AsyncStorage, FlatList, StyleSheet, View} from 'react-native';

import Header from '../components/common/Header';
import PostsListItem from '../components/screen_postslist/PostsListItem';
import {NavigationActions, StackActions} from 'react-navigation';
import ProgressIndicator from '../components/common/ProgressIndicator';
import {
    KEY_FEED_URL_ASYNCSTORAGE,
    KEY_NUMBER_OF_POSTS_ASYNCSTORAGE,
    KEY_UPDATE_INTERVAL_ASYNCSTORAGE
} from "../constants/constants";


const locale = require('../assets/langs/en.json');

let feedUrl;
let numberOfPosts;
let updateInterval;

let sinceId = null;

const LOG_TAG = '>>> PostsList ';

class PostsList extends Component {

    constructor(props) {
        super(props);

        this.state = {
            appState: AppState.currentState,
            showProgress: true,
            posts: []
        };
    }

    componentDidMount() {
        AppState.addEventListener('change', this._handleAppStateChange);
        this.getConfig().then(()=> {
            this.fetchPosts();
            this.interval = setInterval(() =>
                    this.fetchPosts(),
                updateInterval
            );
        });
    }

    componentWillUnmount(){
        AppState.removeEventListener('change', this._handleAppStateChange);
        clearInterval(this.interval);
    }

    _handleAppStateChange = (nextAppState) => {
        if (this.state.appState.match(/inactive|background/) && nextAppState === 'active') {
            this.interval = setInterval(() =>
                    this.fetchPosts(),
                updateInterval
            );
        } else {
            clearInterval(this.interval);
        }
        this.setState({appState: nextAppState});
    };

    async getConfig() {
        feedUrl = await AsyncStorage.getItem(KEY_FEED_URL_ASYNCSTORAGE);
        numberOfPosts = JSON.parse(await AsyncStorage.getItem(KEY_NUMBER_OF_POSTS_ASYNCSTORAGE));
        updateInterval = parseInt(await AsyncStorage.getItem(KEY_UPDATE_INTERVAL_ASYNCSTORAGE))*1000;
        console.log(LOG_TAG, feedUrl, numberOfPosts, updateInterval);
    };

    async fetchPosts() {
        this.setState({
            showProgress: true
        });

        try {
            const response = await fetch(`${feedUrl}?limit=${numberOfPosts}?since_id=${sinceId}`);
            if (response.status >= 200 && response.status < 300) {
                //Handle success
                let data = await response.json();
                this.setState({
                    posts: data
                });
                sinceId = data[0].entity_id;
            } else {
                //Handle error
            }
        } catch
            (error) {
            console.log(LOG_TAG, 'fetchPosts catch', error);
        } finally {
            this.setState({
                showProgress: false
            })
        }
    }

    navigateTo = (routeName) => {
        const navigateAction = StackActions.reset({
            index: 0,
            actions: [NavigationActions.navigate({routeName: routeName})]
        });

        this.props.navigation.dispatch(navigateAction);
    };
    

    keyExtractor = (item, index) => item.id.toString();

    renderItem = ({item}) => (
        <PostsListItem
            id={item.id}
            post={item}/>
    );

    render() {
        return (
            <View style={styles.rootStyle}>
                <Header header={locale.data.postslists_header}/>
                <FlatList
                    contentContainerStyle={styles.contentContainer}
                    data={this.state.posts}
                    extraData={this.state}
                    keyExtractor={this.keyExtractor}
                    renderItem={this.renderItem}
                    updateCellsBatchingPeriod={75}
                    maxToRenderPerBatch={13}
                    initialNumToRender={50}
                    windowSize={21}/>
                {this.state.showProgress &&
                <ProgressIndicator/>
                }
            </View>

        );
    }
}

const styles = StyleSheet.create({
    rootStyle: {
        flex: 1
    },
    contentContainer: {
        justifyContent: 'flex-start',
        alignItems: 'stretch'
    }
});


export default PostsList;
