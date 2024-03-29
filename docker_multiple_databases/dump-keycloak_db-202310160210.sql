PGDMP      
            	    {            keycloak_db    16.0 (Debian 16.0-1.pgdg120+1)    16.0 �   �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            �           1262    16386    keycloak_db    DATABASE     v   CREATE DATABASE keycloak_db WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.utf8';
    DROP DATABASE keycloak_db;
                hospital    false            �           0    0    DATABASE keycloak_db    ACL     2   GRANT ALL ON DATABASE keycloak_db TO keycloak_db;
                   hospital    false    4247                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                pg_database_owner    false            �           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                   pg_database_owner    false    4                        1259    17021    admin_event_entity    TABLE     �  CREATE TABLE public.admin_event_entity (
    id character varying(36) NOT NULL,
    admin_event_time bigint,
    realm_id character varying(255),
    operation_type character varying(255),
    auth_realm_id character varying(255),
    auth_client_id character varying(255),
    auth_user_id character varying(255),
    ip_address character varying(255),
    resource_path character varying(2550),
    representation text,
    error character varying(255),
    resource_type character varying(64)
);
 &   DROP TABLE public.admin_event_entity;
       public         heap    hospital    false    4                       1259    17464    associated_policy    TABLE     �   CREATE TABLE public.associated_policy (
    policy_id character varying(36) NOT NULL,
    associated_policy_id character varying(36) NOT NULL
);
 %   DROP TABLE public.associated_policy;
       public         heap    hospital    false    4                       1259    17036    authentication_execution    TABLE     �  CREATE TABLE public.authentication_execution (
    id character varying(36) NOT NULL,
    alias character varying(255),
    authenticator character varying(36),
    realm_id character varying(36),
    flow_id character varying(36),
    requirement integer,
    priority integer,
    authenticator_flow boolean DEFAULT false NOT NULL,
    auth_flow_id character varying(36),
    auth_config character varying(36)
);
 ,   DROP TABLE public.authentication_execution;
       public         heap    hospital    false    4                       1259    17031    authentication_flow    TABLE     t  CREATE TABLE public.authentication_flow (
    id character varying(36) NOT NULL,
    alias character varying(255),
    description character varying(255),
    realm_id character varying(36),
    provider_id character varying(36) DEFAULT 'basic-flow'::character varying NOT NULL,
    top_level boolean DEFAULT false NOT NULL,
    built_in boolean DEFAULT false NOT NULL
);
 '   DROP TABLE public.authentication_flow;
       public         heap    hospital    false    4                       1259    17026    authenticator_config    TABLE     �   CREATE TABLE public.authenticator_config (
    id character varying(36) NOT NULL,
    alias character varying(255),
    realm_id character varying(36)
);
 (   DROP TABLE public.authenticator_config;
       public         heap    hospital    false    4                       1259    17041    authenticator_config_entry    TABLE     �   CREATE TABLE public.authenticator_config_entry (
    authenticator_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);
 .   DROP TABLE public.authenticator_config_entry;
       public         heap    hospital    false    4                       1259    17479    broker_link    TABLE     L  CREATE TABLE public.broker_link (
    identity_provider character varying(255) NOT NULL,
    storage_provider_id character varying(255),
    realm_id character varying(36) NOT NULL,
    broker_user_id character varying(255),
    broker_username character varying(255),
    token text,
    user_id character varying(255) NOT NULL
);
    DROP TABLE public.broker_link;
       public         heap    hospital    false    4            �            1259    16402    client    TABLE     �  CREATE TABLE public.client (
    id character varying(36) NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    full_scope_allowed boolean DEFAULT false NOT NULL,
    client_id character varying(255),
    not_before integer,
    public_client boolean DEFAULT false NOT NULL,
    secret character varying(255),
    base_url character varying(255),
    bearer_only boolean DEFAULT false NOT NULL,
    management_url character varying(255),
    surrogate_auth_required boolean DEFAULT false NOT NULL,
    realm_id character varying(36),
    protocol character varying(255),
    node_rereg_timeout integer DEFAULT 0,
    frontchannel_logout boolean DEFAULT false NOT NULL,
    consent_required boolean DEFAULT false NOT NULL,
    name character varying(255),
    service_accounts_enabled boolean DEFAULT false NOT NULL,
    client_authenticator_type character varying(255),
    root_url character varying(255),
    description character varying(255),
    registration_token character varying(255),
    standard_flow_enabled boolean DEFAULT true NOT NULL,
    implicit_flow_enabled boolean DEFAULT false NOT NULL,
    direct_access_grants_enabled boolean DEFAULT false NOT NULL,
    always_display_in_console boolean DEFAULT false NOT NULL
);
    DROP TABLE public.client;
       public         heap    hospital    false    4            �            1259    16760    client_attributes    TABLE     �   CREATE TABLE public.client_attributes (
    client_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);
 %   DROP TABLE public.client_attributes;
       public         heap    hospital    false    4            )           1259    17728    client_auth_flow_bindings    TABLE     �   CREATE TABLE public.client_auth_flow_bindings (
    client_id character varying(36) NOT NULL,
    flow_id character varying(36),
    binding_name character varying(255) NOT NULL
);
 -   DROP TABLE public.client_auth_flow_bindings;
       public         heap    hospital    false    4            (           1259    17603    client_initial_access    TABLE     �   CREATE TABLE public.client_initial_access (
    id character varying(36) NOT NULL,
    realm_id character varying(36) NOT NULL,
    "timestamp" integer,
    expiration integer,
    count integer,
    remaining_count integer
);
 )   DROP TABLE public.client_initial_access;
       public         heap    hospital    false    4            �            1259    16770    client_node_registrations    TABLE     �   CREATE TABLE public.client_node_registrations (
    client_id character varying(36) NOT NULL,
    value integer,
    name character varying(255) NOT NULL
);
 -   DROP TABLE public.client_node_registrations;
       public         heap    hospital    false    4                       1259    17269    client_scope    TABLE     �   CREATE TABLE public.client_scope (
    id character varying(36) NOT NULL,
    name character varying(255),
    realm_id character varying(36),
    description character varying(255),
    protocol character varying(255)
);
     DROP TABLE public.client_scope;
       public         heap    hospital    false    4                       1259    17283    client_scope_attributes    TABLE     �   CREATE TABLE public.client_scope_attributes (
    scope_id character varying(36) NOT NULL,
    value character varying(2048),
    name character varying(255) NOT NULL
);
 +   DROP TABLE public.client_scope_attributes;
       public         heap    hospital    false    4            *           1259    17769    client_scope_client    TABLE     �   CREATE TABLE public.client_scope_client (
    client_id character varying(255) NOT NULL,
    scope_id character varying(255) NOT NULL,
    default_scope boolean DEFAULT false NOT NULL
);
 '   DROP TABLE public.client_scope_client;
       public         heap    hospital    false    4                       1259    17288    client_scope_role_mapping    TABLE     �   CREATE TABLE public.client_scope_role_mapping (
    scope_id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL
);
 -   DROP TABLE public.client_scope_role_mapping;
       public         heap    hospital    false    4            �            1259    16413    client_session    TABLE     �  CREATE TABLE public.client_session (
    id character varying(36) NOT NULL,
    client_id character varying(36),
    redirect_uri character varying(255),
    state character varying(255),
    "timestamp" integer,
    session_id character varying(36),
    auth_method character varying(255),
    realm_id character varying(255),
    auth_user_id character varying(36),
    current_action character varying(36)
);
 "   DROP TABLE public.client_session;
       public         heap    hospital    false    4                       1259    17059    client_session_auth_status    TABLE     �   CREATE TABLE public.client_session_auth_status (
    authenticator character varying(36) NOT NULL,
    status integer,
    client_session character varying(36) NOT NULL
);
 .   DROP TABLE public.client_session_auth_status;
       public         heap    hospital    false    4            �            1259    16765    client_session_note    TABLE     �   CREATE TABLE public.client_session_note (
    name character varying(255) NOT NULL,
    value character varying(255),
    client_session character varying(36) NOT NULL
);
 '   DROP TABLE public.client_session_note;
       public         heap    hospital    false    4            �            1259    16943    client_session_prot_mapper    TABLE     �   CREATE TABLE public.client_session_prot_mapper (
    protocol_mapper_id character varying(36) NOT NULL,
    client_session character varying(36) NOT NULL
);
 .   DROP TABLE public.client_session_prot_mapper;
       public         heap    hospital    false    4            �            1259    16418    client_session_role    TABLE     �   CREATE TABLE public.client_session_role (
    role_id character varying(255) NOT NULL,
    client_session character varying(36) NOT NULL
);
 '   DROP TABLE public.client_session_role;
       public         heap    hospital    false    4                       1259    17140    client_user_session_note    TABLE     �   CREATE TABLE public.client_user_session_note (
    name character varying(255) NOT NULL,
    value character varying(2048),
    client_session character varying(36) NOT NULL
);
 ,   DROP TABLE public.client_user_session_note;
       public         heap    hospital    false    4            &           1259    17524 	   component    TABLE     )  CREATE TABLE public.component (
    id character varying(36) NOT NULL,
    name character varying(255),
    parent_id character varying(36),
    provider_id character varying(36),
    provider_type character varying(255),
    realm_id character varying(36),
    sub_type character varying(255)
);
    DROP TABLE public.component;
       public         heap    hospital    false    4            %           1259    17519    component_config    TABLE     �   CREATE TABLE public.component_config (
    id character varying(36) NOT NULL,
    component_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(4000)
);
 $   DROP TABLE public.component_config;
       public         heap    hospital    false    4            �            1259    16421    composite_role    TABLE     �   CREATE TABLE public.composite_role (
    composite character varying(36) NOT NULL,
    child_role character varying(36) NOT NULL
);
 "   DROP TABLE public.composite_role;
       public         heap    hospital    false    4            �            1259    16424 
   credential    TABLE     $  CREATE TABLE public.credential (
    id character varying(36) NOT NULL,
    salt bytea,
    type character varying(255),
    user_id character varying(36),
    created_date bigint,
    user_label character varying(255),
    secret_data text,
    credential_data text,
    priority integer
);
    DROP TABLE public.credential;
       public         heap    hospital    false    4            �            1259    16394    databasechangelog    TABLE     Y  CREATE TABLE public.databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255),
    deployment_id character varying(10)
);
 %   DROP TABLE public.databasechangelog;
       public         heap    hospital    false    4            �            1259    16389    databasechangeloglock    TABLE     �   CREATE TABLE public.databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);
 )   DROP TABLE public.databasechangeloglock;
       public         heap    hospital    false    4            +           1259    17785    default_client_scope    TABLE     �   CREATE TABLE public.default_client_scope (
    realm_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL,
    default_scope boolean DEFAULT false NOT NULL
);
 (   DROP TABLE public.default_client_scope;
       public         heap    hospital    false    4            �            1259    16429    event_entity    TABLE     �  CREATE TABLE public.event_entity (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    details_json character varying(2550),
    error character varying(255),
    ip_address character varying(255),
    realm_id character varying(255),
    session_id character varying(255),
    event_time bigint,
    type character varying(255),
    user_id character varying(255)
);
     DROP TABLE public.event_entity;
       public         heap    hospital    false    4                       1259    17484    fed_user_attribute    TABLE     (  CREATE TABLE public.fed_user_attribute (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    value character varying(2024)
);
 &   DROP TABLE public.fed_user_attribute;
       public         heap    hospital    false    4                        1259    17489    fed_user_consent    TABLE     �  CREATE TABLE public.fed_user_consent (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    created_date bigint,
    last_updated_date bigint,
    client_storage_provider character varying(36),
    external_client_id character varying(255)
);
 $   DROP TABLE public.fed_user_consent;
       public         heap    hospital    false    4            -           1259    17811    fed_user_consent_cl_scope    TABLE     �   CREATE TABLE public.fed_user_consent_cl_scope (
    user_consent_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);
 -   DROP TABLE public.fed_user_consent_cl_scope;
       public         heap    hospital    false    4            !           1259    17498    fed_user_credential    TABLE     �  CREATE TABLE public.fed_user_credential (
    id character varying(36) NOT NULL,
    salt bytea,
    type character varying(255),
    created_date bigint,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    user_label character varying(255),
    secret_data text,
    credential_data text,
    priority integer
);
 '   DROP TABLE public.fed_user_credential;
       public         heap    hospital    false    4            "           1259    17507    fed_user_group_membership    TABLE     �   CREATE TABLE public.fed_user_group_membership (
    group_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);
 -   DROP TABLE public.fed_user_group_membership;
       public         heap    hospital    false    4            #           1259    17510    fed_user_required_action    TABLE       CREATE TABLE public.fed_user_required_action (
    required_action character varying(255) DEFAULT ' '::character varying NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);
 ,   DROP TABLE public.fed_user_required_action;
       public         heap    hospital    false    4            $           1259    17516    fed_user_role_mapping    TABLE     �   CREATE TABLE public.fed_user_role_mapping (
    role_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);
 )   DROP TABLE public.fed_user_role_mapping;
       public         heap    hospital    false    4            �            1259    16806    federated_identity    TABLE       CREATE TABLE public.federated_identity (
    identity_provider character varying(255) NOT NULL,
    realm_id character varying(36),
    federated_user_id character varying(255),
    federated_username character varying(255),
    token text,
    user_id character varying(36) NOT NULL
);
 &   DROP TABLE public.federated_identity;
       public         heap    hospital    false    4            '           1259    17581    federated_user    TABLE     �   CREATE TABLE public.federated_user (
    id character varying(255) NOT NULL,
    storage_provider_id character varying(255),
    realm_id character varying(36) NOT NULL
);
 "   DROP TABLE public.federated_user;
       public         heap    hospital    false    4                       1259    17208    group_attribute    TABLE       CREATE TABLE public.group_attribute (
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255),
    group_id character varying(36) NOT NULL
);
 #   DROP TABLE public.group_attribute;
       public         heap    hospital    false    4                       1259    17205    group_role_mapping    TABLE     �   CREATE TABLE public.group_role_mapping (
    role_id character varying(36) NOT NULL,
    group_id character varying(36) NOT NULL
);
 &   DROP TABLE public.group_role_mapping;
       public         heap    hospital    false    4            �            1259    16811    identity_provider    TABLE     �  CREATE TABLE public.identity_provider (
    internal_id character varying(36) NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    provider_alias character varying(255),
    provider_id character varying(255),
    store_token boolean DEFAULT false NOT NULL,
    authenticate_by_default boolean DEFAULT false NOT NULL,
    realm_id character varying(36),
    add_token_role boolean DEFAULT true NOT NULL,
    trust_email boolean DEFAULT false NOT NULL,
    first_broker_login_flow_id character varying(36),
    post_broker_login_flow_id character varying(36),
    provider_display_name character varying(255),
    link_only boolean DEFAULT false NOT NULL
);
 %   DROP TABLE public.identity_provider;
       public         heap    hospital    false    4            �            1259    16820    identity_provider_config    TABLE     �   CREATE TABLE public.identity_provider_config (
    identity_provider_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);
 ,   DROP TABLE public.identity_provider_config;
       public         heap    hospital    false    4            �            1259    16924    identity_provider_mapper    TABLE       CREATE TABLE public.identity_provider_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    idp_alias character varying(255) NOT NULL,
    idp_mapper_name character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL
);
 ,   DROP TABLE public.identity_provider_mapper;
       public         heap    hospital    false    4            �            1259    16929    idp_mapper_config    TABLE     �   CREATE TABLE public.idp_mapper_config (
    idp_mapper_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);
 %   DROP TABLE public.idp_mapper_config;
       public         heap    hospital    false    4                       1259    17202    keycloak_group    TABLE     �   CREATE TABLE public.keycloak_group (
    id character varying(36) NOT NULL,
    name character varying(255),
    parent_group character varying(36) NOT NULL,
    realm_id character varying(36)
);
 "   DROP TABLE public.keycloak_group;
       public         heap    hospital    false    4            �            1259    16437    keycloak_role    TABLE     b  CREATE TABLE public.keycloak_role (
    id character varying(36) NOT NULL,
    client_realm_constraint character varying(255),
    client_role boolean DEFAULT false NOT NULL,
    description character varying(255),
    name character varying(255),
    realm_id character varying(255),
    client character varying(36),
    realm character varying(36)
);
 !   DROP TABLE public.keycloak_role;
       public         heap    hospital    false    4            �            1259    16921    migration_model    TABLE     �   CREATE TABLE public.migration_model (
    id character varying(36) NOT NULL,
    version character varying(36),
    update_time bigint DEFAULT 0 NOT NULL
);
 #   DROP TABLE public.migration_model;
       public         heap    hospital    false    4                       1259    17193    offline_client_session    TABLE     �  CREATE TABLE public.offline_client_session (
    user_session_id character varying(36) NOT NULL,
    client_id character varying(255) NOT NULL,
    offline_flag character varying(4) NOT NULL,
    "timestamp" integer,
    data text,
    client_storage_provider character varying(36) DEFAULT 'local'::character varying NOT NULL,
    external_client_id character varying(255) DEFAULT 'local'::character varying NOT NULL
);
 *   DROP TABLE public.offline_client_session;
       public         heap    hospital    false    4                       1259    17188    offline_user_session    TABLE     P  CREATE TABLE public.offline_user_session (
    user_session_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    created_on integer NOT NULL,
    offline_flag character varying(4) NOT NULL,
    data text,
    last_session_refresh integer DEFAULT 0 NOT NULL
);
 (   DROP TABLE public.offline_user_session;
       public         heap    hospital    false    4                       1259    17407    policy_config    TABLE     �   CREATE TABLE public.policy_config (
    policy_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);
 !   DROP TABLE public.policy_config;
       public         heap    hospital    false    4            �            1259    16795    protocol_mapper    TABLE     1  CREATE TABLE public.protocol_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    protocol character varying(255) NOT NULL,
    protocol_mapper_name character varying(255) NOT NULL,
    client_id character varying(36),
    client_scope_id character varying(36)
);
 #   DROP TABLE public.protocol_mapper;
       public         heap    hospital    false    4            �            1259    16801    protocol_mapper_config    TABLE     �   CREATE TABLE public.protocol_mapper_config (
    protocol_mapper_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);
 *   DROP TABLE public.protocol_mapper_config;
       public         heap    hospital    false    4            �            1259    16443    realm    TABLE     �	  CREATE TABLE public.realm (
    id character varying(36) NOT NULL,
    access_code_lifespan integer,
    user_action_lifespan integer,
    access_token_lifespan integer,
    account_theme character varying(255),
    admin_theme character varying(255),
    email_theme character varying(255),
    enabled boolean DEFAULT false NOT NULL,
    events_enabled boolean DEFAULT false NOT NULL,
    events_expiration bigint,
    login_theme character varying(255),
    name character varying(255),
    not_before integer,
    password_policy character varying(2550),
    registration_allowed boolean DEFAULT false NOT NULL,
    remember_me boolean DEFAULT false NOT NULL,
    reset_password_allowed boolean DEFAULT false NOT NULL,
    social boolean DEFAULT false NOT NULL,
    ssl_required character varying(255),
    sso_idle_timeout integer,
    sso_max_lifespan integer,
    update_profile_on_soc_login boolean DEFAULT false NOT NULL,
    verify_email boolean DEFAULT false NOT NULL,
    master_admin_client character varying(36),
    login_lifespan integer,
    internationalization_enabled boolean DEFAULT false NOT NULL,
    default_locale character varying(255),
    reg_email_as_username boolean DEFAULT false NOT NULL,
    admin_events_enabled boolean DEFAULT false NOT NULL,
    admin_events_details_enabled boolean DEFAULT false NOT NULL,
    edit_username_allowed boolean DEFAULT false NOT NULL,
    otp_policy_counter integer DEFAULT 0,
    otp_policy_window integer DEFAULT 1,
    otp_policy_period integer DEFAULT 30,
    otp_policy_digits integer DEFAULT 6,
    otp_policy_alg character varying(36) DEFAULT 'HmacSHA1'::character varying,
    otp_policy_type character varying(36) DEFAULT 'totp'::character varying,
    browser_flow character varying(36),
    registration_flow character varying(36),
    direct_grant_flow character varying(36),
    reset_credentials_flow character varying(36),
    client_auth_flow character varying(36),
    offline_session_idle_timeout integer DEFAULT 0,
    revoke_refresh_token boolean DEFAULT false NOT NULL,
    access_token_life_implicit integer DEFAULT 0,
    login_with_email_allowed boolean DEFAULT true NOT NULL,
    duplicate_emails_allowed boolean DEFAULT false NOT NULL,
    docker_auth_flow character varying(36),
    refresh_token_max_reuse integer DEFAULT 0,
    allow_user_managed_access boolean DEFAULT false NOT NULL,
    sso_max_lifespan_remember_me integer DEFAULT 0 NOT NULL,
    sso_idle_timeout_remember_me integer DEFAULT 0 NOT NULL,
    default_role character varying(255)
);
    DROP TABLE public.realm;
       public         heap    hospital    false    4            �            1259    16460    realm_attribute    TABLE     �   CREATE TABLE public.realm_attribute (
    name character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    value text
);
 #   DROP TABLE public.realm_attribute;
       public         heap    hospital    false    4                       1259    17217    realm_default_groups    TABLE     �   CREATE TABLE public.realm_default_groups (
    realm_id character varying(36) NOT NULL,
    group_id character varying(36) NOT NULL
);
 (   DROP TABLE public.realm_default_groups;
       public         heap    hospital    false    4            �            1259    16913    realm_enabled_event_types    TABLE     �   CREATE TABLE public.realm_enabled_event_types (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);
 -   DROP TABLE public.realm_enabled_event_types;
       public         heap    hospital    false    4            �            1259    16468    realm_events_listeners    TABLE     �   CREATE TABLE public.realm_events_listeners (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);
 *   DROP TABLE public.realm_events_listeners;
       public         heap    hospital    false    4            2           1259    17919    realm_localizations    TABLE     �   CREATE TABLE public.realm_localizations (
    realm_id character varying(255) NOT NULL,
    locale character varying(255) NOT NULL,
    texts text NOT NULL
);
 '   DROP TABLE public.realm_localizations;
       public         heap    hospital    false    4            �            1259    16471    realm_required_credential    TABLE       CREATE TABLE public.realm_required_credential (
    type character varying(255) NOT NULL,
    form_label character varying(255),
    input boolean DEFAULT false NOT NULL,
    secret boolean DEFAULT false NOT NULL,
    realm_id character varying(36) NOT NULL
);
 -   DROP TABLE public.realm_required_credential;
       public         heap    hospital    false    4            �            1259    16478    realm_smtp_config    TABLE     �   CREATE TABLE public.realm_smtp_config (
    realm_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);
 %   DROP TABLE public.realm_smtp_config;
       public         heap    hospital    false    4            �            1259    16829    realm_supported_locales    TABLE     �   CREATE TABLE public.realm_supported_locales (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);
 +   DROP TABLE public.realm_supported_locales;
       public         heap    hospital    false    4            �            1259    16488    redirect_uris    TABLE        CREATE TABLE public.redirect_uris (
    client_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);
 !   DROP TABLE public.redirect_uris;
       public         heap    hospital    false    4            
           1259    17152    required_action_config    TABLE     �   CREATE TABLE public.required_action_config (
    required_action_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);
 *   DROP TABLE public.required_action_config;
       public         heap    hospital    false    4            	           1259    17145    required_action_provider    TABLE     \  CREATE TABLE public.required_action_provider (
    id character varying(36) NOT NULL,
    alias character varying(255),
    name character varying(255),
    realm_id character varying(36),
    enabled boolean DEFAULT false NOT NULL,
    default_action boolean DEFAULT false NOT NULL,
    provider_id character varying(255),
    priority integer
);
 ,   DROP TABLE public.required_action_provider;
       public         heap    hospital    false    4            /           1259    17850    resource_attribute    TABLE       CREATE TABLE public.resource_attribute (
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255),
    resource_id character varying(36) NOT NULL
);
 &   DROP TABLE public.resource_attribute;
       public         heap    hospital    false    4                       1259    17434    resource_policy    TABLE     �   CREATE TABLE public.resource_policy (
    resource_id character varying(36) NOT NULL,
    policy_id character varying(36) NOT NULL
);
 #   DROP TABLE public.resource_policy;
       public         heap    hospital    false    4                       1259    17419    resource_scope    TABLE     �   CREATE TABLE public.resource_scope (
    resource_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);
 "   DROP TABLE public.resource_scope;
       public         heap    hospital    false    4                       1259    17357    resource_server    TABLE     �   CREATE TABLE public.resource_server (
    id character varying(36) NOT NULL,
    allow_rs_remote_mgmt boolean DEFAULT false NOT NULL,
    policy_enforce_mode character varying(15) NOT NULL,
    decision_strategy smallint DEFAULT 1 NOT NULL
);
 #   DROP TABLE public.resource_server;
       public         heap    hospital    false    4            .           1259    17826    resource_server_perm_ticket    TABLE     �  CREATE TABLE public.resource_server_perm_ticket (
    id character varying(36) NOT NULL,
    owner character varying(255) NOT NULL,
    requester character varying(255) NOT NULL,
    created_timestamp bigint NOT NULL,
    granted_timestamp bigint,
    resource_id character varying(36) NOT NULL,
    scope_id character varying(36),
    resource_server_id character varying(36) NOT NULL,
    policy_id character varying(36)
);
 /   DROP TABLE public.resource_server_perm_ticket;
       public         heap    hospital    false    4                       1259    17393    resource_server_policy    TABLE     y  CREATE TABLE public.resource_server_policy (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255),
    type character varying(255) NOT NULL,
    decision_strategy character varying(20),
    logic character varying(20),
    resource_server_id character varying(36) NOT NULL,
    owner character varying(255)
);
 *   DROP TABLE public.resource_server_policy;
       public         heap    hospital    false    4                       1259    17365    resource_server_resource    TABLE     �  CREATE TABLE public.resource_server_resource (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    type character varying(255),
    icon_uri character varying(255),
    owner character varying(255) NOT NULL,
    resource_server_id character varying(36) NOT NULL,
    owner_managed_access boolean DEFAULT false NOT NULL,
    display_name character varying(255)
);
 ,   DROP TABLE public.resource_server_resource;
       public         heap    hospital    false    4                       1259    17379    resource_server_scope    TABLE       CREATE TABLE public.resource_server_scope (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    icon_uri character varying(255),
    resource_server_id character varying(36) NOT NULL,
    display_name character varying(255)
);
 )   DROP TABLE public.resource_server_scope;
       public         heap    hospital    false    4            0           1259    17868    resource_uris    TABLE     �   CREATE TABLE public.resource_uris (
    resource_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);
 !   DROP TABLE public.resource_uris;
       public         heap    hospital    false    4            1           1259    17878    role_attribute    TABLE     �   CREATE TABLE public.role_attribute (
    id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255)
);
 "   DROP TABLE public.role_attribute;
       public         heap    hospital    false    4            �            1259    16491    scope_mapping    TABLE     �   CREATE TABLE public.scope_mapping (
    client_id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL
);
 !   DROP TABLE public.scope_mapping;
       public         heap    hospital    false    4                       1259    17449    scope_policy    TABLE     �   CREATE TABLE public.scope_policy (
    scope_id character varying(36) NOT NULL,
    policy_id character varying(36) NOT NULL
);
     DROP TABLE public.scope_policy;
       public         heap    hospital    false    4            �            1259    16497    user_attribute    TABLE     �   CREATE TABLE public.user_attribute (
    name character varying(255) NOT NULL,
    value character varying(255),
    user_id character varying(36) NOT NULL,
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL
);
 "   DROP TABLE public.user_attribute;
       public         heap    hospital    false    4            �            1259    16934    user_consent    TABLE     7  CREATE TABLE public.user_consent (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    user_id character varying(36) NOT NULL,
    created_date bigint,
    last_updated_date bigint,
    client_storage_provider character varying(36),
    external_client_id character varying(255)
);
     DROP TABLE public.user_consent;
       public         heap    hospital    false    4            ,           1259    17801    user_consent_client_scope    TABLE     �   CREATE TABLE public.user_consent_client_scope (
    user_consent_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);
 -   DROP TABLE public.user_consent_client_scope;
       public         heap    hospital    false    4            �            1259    16502    user_entity    TABLE     =  CREATE TABLE public.user_entity (
    id character varying(36) NOT NULL,
    email character varying(255),
    email_constraint character varying(255),
    email_verified boolean DEFAULT false NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    federation_link character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    realm_id character varying(255),
    username character varying(255),
    created_timestamp bigint,
    service_account_client_link character varying(255),
    not_before integer DEFAULT 0 NOT NULL
);
    DROP TABLE public.user_entity;
       public         heap    hospital    false    4            �            1259    16510    user_federation_config    TABLE     �   CREATE TABLE public.user_federation_config (
    user_federation_provider_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);
 *   DROP TABLE public.user_federation_config;
       public         heap    hospital    false    4                       1259    17046    user_federation_mapper    TABLE     $  CREATE TABLE public.user_federation_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    federation_provider_id character varying(36) NOT NULL,
    federation_mapper_type character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL
);
 *   DROP TABLE public.user_federation_mapper;
       public         heap    hospital    false    4                       1259    17051    user_federation_mapper_config    TABLE     �   CREATE TABLE public.user_federation_mapper_config (
    user_federation_mapper_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);
 1   DROP TABLE public.user_federation_mapper_config;
       public         heap    hospital    false    4            �            1259    16515    user_federation_provider    TABLE     ;  CREATE TABLE public.user_federation_provider (
    id character varying(36) NOT NULL,
    changed_sync_period integer,
    display_name character varying(255),
    full_sync_period integer,
    last_sync integer,
    priority integer,
    provider_name character varying(255),
    realm_id character varying(36)
);
 ,   DROP TABLE public.user_federation_provider;
       public         heap    hospital    false    4                       1259    17214    user_group_membership    TABLE     �   CREATE TABLE public.user_group_membership (
    group_id character varying(36) NOT NULL,
    user_id character varying(36) NOT NULL
);
 )   DROP TABLE public.user_group_membership;
       public         heap    hospital    false    4            �            1259    16520    user_required_action    TABLE     �   CREATE TABLE public.user_required_action (
    user_id character varying(36) NOT NULL,
    required_action character varying(255) DEFAULT ' '::character varying NOT NULL
);
 (   DROP TABLE public.user_required_action;
       public         heap    hospital    false    4            �            1259    16523    user_role_mapping    TABLE     �   CREATE TABLE public.user_role_mapping (
    role_id character varying(255) NOT NULL,
    user_id character varying(36) NOT NULL
);
 %   DROP TABLE public.user_role_mapping;
       public         heap    hospital    false    4            �            1259    16526    user_session    TABLE     �  CREATE TABLE public.user_session (
    id character varying(36) NOT NULL,
    auth_method character varying(255),
    ip_address character varying(255),
    last_session_refresh integer,
    login_username character varying(255),
    realm_id character varying(255),
    remember_me boolean DEFAULT false NOT NULL,
    started integer,
    user_id character varying(255),
    user_session_state integer,
    broker_session_id character varying(255),
    broker_user_id character varying(255)
);
     DROP TABLE public.user_session;
       public         heap    hospital    false    4            �            1259    16832    user_session_note    TABLE     �   CREATE TABLE public.user_session_note (
    user_session character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(2048)
);
 %   DROP TABLE public.user_session_note;
       public         heap    hospital    false    4            �            1259    16494    username_login_failure    TABLE       CREATE TABLE public.username_login_failure (
    realm_id character varying(36) NOT NULL,
    username character varying(255) NOT NULL,
    failed_login_not_before integer,
    last_failure bigint,
    last_ip_failure character varying(255),
    num_failures integer
);
 *   DROP TABLE public.username_login_failure;
       public         heap    hospital    false    4            �            1259    16537    web_origins    TABLE     }   CREATE TABLE public.web_origins (
    client_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);
    DROP TABLE public.web_origins;
       public         heap    hospital    false    4            _          0    17021    admin_event_entity 
   TABLE DATA           �   COPY public.admin_event_entity (id, admin_event_time, realm_id, operation_type, auth_realm_id, auth_client_id, auth_user_id, ip_address, resource_path, representation, error, resource_type) FROM stdin;
    public          hospital    false    256   �      |          0    17464    associated_policy 
   TABLE DATA           L   COPY public.associated_policy (policy_id, associated_policy_id) FROM stdin;
    public          hospital    false    285   �      b          0    17036    authentication_execution 
   TABLE DATA           �   COPY public.authentication_execution (id, alias, authenticator, realm_id, flow_id, requirement, priority, authenticator_flow, auth_flow_id, auth_config) FROM stdin;
    public          hospital    false    259   �      a          0    17031    authentication_flow 
   TABLE DATA           q   COPY public.authentication_flow (id, alias, description, realm_id, provider_id, top_level, built_in) FROM stdin;
    public          hospital    false    258   ��      `          0    17026    authenticator_config 
   TABLE DATA           C   COPY public.authenticator_config (id, alias, realm_id) FROM stdin;
    public          hospital    false    257   ��      c          0    17041    authenticator_config_entry 
   TABLE DATA           S   COPY public.authenticator_config_entry (authenticator_id, value, name) FROM stdin;
    public          hospital    false    260   ��      }          0    17479    broker_link 
   TABLE DATA           �   COPY public.broker_link (identity_provider, storage_provider_id, realm_id, broker_user_id, broker_username, token, user_id) FROM stdin;
    public          hospital    false    286   Y�      8          0    16402    client 
   TABLE DATA           �  COPY public.client (id, enabled, full_scope_allowed, client_id, not_before, public_client, secret, base_url, bearer_only, management_url, surrogate_auth_required, realm_id, protocol, node_rereg_timeout, frontchannel_logout, consent_required, name, service_accounts_enabled, client_authenticator_type, root_url, description, registration_token, standard_flow_enabled, implicit_flow_enabled, direct_access_grants_enabled, always_display_in_console) FROM stdin;
    public          hospital    false    217   v�      O          0    16760    client_attributes 
   TABLE DATA           C   COPY public.client_attributes (client_id, name, value) FROM stdin;
    public          hospital    false    240   r�      �          0    17728    client_auth_flow_bindings 
   TABLE DATA           U   COPY public.client_auth_flow_bindings (client_id, flow_id, binding_name) FROM stdin;
    public          hospital    false    297   "�      �          0    17603    client_initial_access 
   TABLE DATA           n   COPY public.client_initial_access (id, realm_id, "timestamp", expiration, count, remaining_count) FROM stdin;
    public          hospital    false    296   ?�      Q          0    16770    client_node_registrations 
   TABLE DATA           K   COPY public.client_node_registrations (client_id, value, name) FROM stdin;
    public          hospital    false    242   \�      q          0    17269    client_scope 
   TABLE DATA           Q   COPY public.client_scope (id, name, realm_id, description, protocol) FROM stdin;
    public          hospital    false    274   y�      r          0    17283    client_scope_attributes 
   TABLE DATA           H   COPY public.client_scope_attributes (scope_id, value, name) FROM stdin;
    public          hospital    false    275   ۡ      �          0    17769    client_scope_client 
   TABLE DATA           Q   COPY public.client_scope_client (client_id, scope_id, default_scope) FROM stdin;
    public          hospital    false    298   ��      s          0    17288    client_scope_role_mapping 
   TABLE DATA           F   COPY public.client_scope_role_mapping (scope_id, role_id) FROM stdin;
    public          hospital    false    276   ?�      9          0    16413    client_session 
   TABLE DATA           �   COPY public.client_session (id, client_id, redirect_uri, state, "timestamp", session_id, auth_method, realm_id, auth_user_id, current_action) FROM stdin;
    public          hospital    false    218   ©      f          0    17059    client_session_auth_status 
   TABLE DATA           [   COPY public.client_session_auth_status (authenticator, status, client_session) FROM stdin;
    public          hospital    false    263   ߩ      P          0    16765    client_session_note 
   TABLE DATA           J   COPY public.client_session_note (name, value, client_session) FROM stdin;
    public          hospital    false    241   ��      ^          0    16943    client_session_prot_mapper 
   TABLE DATA           X   COPY public.client_session_prot_mapper (protocol_mapper_id, client_session) FROM stdin;
    public          hospital    false    255   �      :          0    16418    client_session_role 
   TABLE DATA           F   COPY public.client_session_role (role_id, client_session) FROM stdin;
    public          hospital    false    219   6�      g          0    17140    client_user_session_note 
   TABLE DATA           O   COPY public.client_user_session_note (name, value, client_session) FROM stdin;
    public          hospital    false    264   S�      �          0    17524 	   component 
   TABLE DATA           h   COPY public.component (id, name, parent_id, provider_id, provider_type, realm_id, sub_type) FROM stdin;
    public          hospital    false    294   p�      �          0    17519    component_config 
   TABLE DATA           I   COPY public.component_config (id, component_id, name, value) FROM stdin;
    public          hospital    false    293   ��      ;          0    16421    composite_role 
   TABLE DATA           ?   COPY public.composite_role (composite, child_role) FROM stdin;
    public          hospital    false    220   �      <          0    16424 
   credential 
   TABLE DATA              COPY public.credential (id, salt, type, user_id, created_date, user_label, secret_data, credential_data, priority) FROM stdin;
    public          hospital    false    221   ��      7          0    16394    databasechangelog 
   TABLE DATA           �   COPY public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) FROM stdin;
    public          hospital    false    216   ��      6          0    16389    databasechangeloglock 
   TABLE DATA           R   COPY public.databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
    public          hospital    false    215   ��      �          0    17785    default_client_scope 
   TABLE DATA           Q   COPY public.default_client_scope (realm_id, scope_id, default_scope) FROM stdin;
    public          hospital    false    299   ��      =          0    16429    event_entity 
   TABLE DATA           �   COPY public.event_entity (id, client_id, details_json, error, ip_address, realm_id, session_id, event_time, type, user_id) FROM stdin;
    public          hospital    false    222   ��      ~          0    17484    fed_user_attribute 
   TABLE DATA           e   COPY public.fed_user_attribute (id, name, user_id, realm_id, storage_provider_id, value) FROM stdin;
    public          hospital    false    287   ��                0    17489    fed_user_consent 
   TABLE DATA           �   COPY public.fed_user_consent (id, client_id, user_id, realm_id, storage_provider_id, created_date, last_updated_date, client_storage_provider, external_client_id) FROM stdin;
    public          hospital    false    288   �      �          0    17811    fed_user_consent_cl_scope 
   TABLE DATA           N   COPY public.fed_user_consent_cl_scope (user_consent_id, scope_id) FROM stdin;
    public          hospital    false    301   !�      �          0    17498    fed_user_credential 
   TABLE DATA           �   COPY public.fed_user_credential (id, salt, type, created_date, user_id, realm_id, storage_provider_id, user_label, secret_data, credential_data, priority) FROM stdin;
    public          hospital    false    289   >�      �          0    17507    fed_user_group_membership 
   TABLE DATA           e   COPY public.fed_user_group_membership (group_id, user_id, realm_id, storage_provider_id) FROM stdin;
    public          hospital    false    290   [�      �          0    17510    fed_user_required_action 
   TABLE DATA           k   COPY public.fed_user_required_action (required_action, user_id, realm_id, storage_provider_id) FROM stdin;
    public          hospital    false    291   x�      �          0    17516    fed_user_role_mapping 
   TABLE DATA           `   COPY public.fed_user_role_mapping (role_id, user_id, realm_id, storage_provider_id) FROM stdin;
    public          hospital    false    292   ��      T          0    16806    federated_identity 
   TABLE DATA           �   COPY public.federated_identity (identity_provider, realm_id, federated_user_id, federated_username, token, user_id) FROM stdin;
    public          hospital    false    245   ��      �          0    17581    federated_user 
   TABLE DATA           K   COPY public.federated_user (id, storage_provider_id, realm_id) FROM stdin;
    public          hospital    false    295   ��      n          0    17208    group_attribute 
   TABLE DATA           D   COPY public.group_attribute (id, name, value, group_id) FROM stdin;
    public          hospital    false    271   ��      m          0    17205    group_role_mapping 
   TABLE DATA           ?   COPY public.group_role_mapping (role_id, group_id) FROM stdin;
    public          hospital    false    270   	�      U          0    16811    identity_provider 
   TABLE DATA             COPY public.identity_provider (internal_id, enabled, provider_alias, provider_id, store_token, authenticate_by_default, realm_id, add_token_role, trust_email, first_broker_login_flow_id, post_broker_login_flow_id, provider_display_name, link_only) FROM stdin;
    public          hospital    false    246   &�      V          0    16820    identity_provider_config 
   TABLE DATA           U   COPY public.identity_provider_config (identity_provider_id, value, name) FROM stdin;
    public          hospital    false    247   C�      [          0    16924    identity_provider_mapper 
   TABLE DATA           b   COPY public.identity_provider_mapper (id, name, idp_alias, idp_mapper_name, realm_id) FROM stdin;
    public          hospital    false    252   `�      \          0    16929    idp_mapper_config 
   TABLE DATA           G   COPY public.idp_mapper_config (idp_mapper_id, value, name) FROM stdin;
    public          hospital    false    253   }�      l          0    17202    keycloak_group 
   TABLE DATA           J   COPY public.keycloak_group (id, name, parent_group, realm_id) FROM stdin;
    public          hospital    false    269   ��      >          0    16437    keycloak_role 
   TABLE DATA           }   COPY public.keycloak_role (id, client_realm_constraint, client_role, description, name, realm_id, client, realm) FROM stdin;
    public          hospital    false    223   ��      Z          0    16921    migration_model 
   TABLE DATA           C   COPY public.migration_model (id, version, update_time) FROM stdin;
    public          hospital    false    251   >      k          0    17193    offline_client_session 
   TABLE DATA           �   COPY public.offline_client_session (user_session_id, client_id, offline_flag, "timestamp", data, client_storage_provider, external_client_id) FROM stdin;
    public          hospital    false    268   s      j          0    17188    offline_user_session 
   TABLE DATA           �   COPY public.offline_user_session (user_session_id, user_id, realm_id, created_on, offline_flag, data, last_session_refresh) FROM stdin;
    public          hospital    false    267   �      x          0    17407    policy_config 
   TABLE DATA           ?   COPY public.policy_config (policy_id, name, value) FROM stdin;
    public          hospital    false    281   �      R          0    16795    protocol_mapper 
   TABLE DATA           o   COPY public.protocol_mapper (id, name, protocol, protocol_mapper_name, client_id, client_scope_id) FROM stdin;
    public          hospital    false    243   �      S          0    16801    protocol_mapper_config 
   TABLE DATA           Q   COPY public.protocol_mapper_config (protocol_mapper_id, value, name) FROM stdin;
    public          hospital    false    244   �      ?          0    16443    realm 
   TABLE DATA              COPY public.realm (id, access_code_lifespan, user_action_lifespan, access_token_lifespan, account_theme, admin_theme, email_theme, enabled, events_enabled, events_expiration, login_theme, name, not_before, password_policy, registration_allowed, remember_me, reset_password_allowed, social, ssl_required, sso_idle_timeout, sso_max_lifespan, update_profile_on_soc_login, verify_email, master_admin_client, login_lifespan, internationalization_enabled, default_locale, reg_email_as_username, admin_events_enabled, admin_events_details_enabled, edit_username_allowed, otp_policy_counter, otp_policy_window, otp_policy_period, otp_policy_digits, otp_policy_alg, otp_policy_type, browser_flow, registration_flow, direct_grant_flow, reset_credentials_flow, client_auth_flow, offline_session_idle_timeout, revoke_refresh_token, access_token_life_implicit, login_with_email_allowed, duplicate_emails_allowed, docker_auth_flow, refresh_token_max_reuse, allow_user_managed_access, sso_max_lifespan_remember_me, sso_idle_timeout_remember_me, default_role) FROM stdin;
    public          hospital    false    224   z      @          0    16460    realm_attribute 
   TABLE DATA           @   COPY public.realm_attribute (name, realm_id, value) FROM stdin;
    public          hospital    false    225   �!      p          0    17217    realm_default_groups 
   TABLE DATA           B   COPY public.realm_default_groups (realm_id, group_id) FROM stdin;
    public          hospital    false    273   
&      Y          0    16913    realm_enabled_event_types 
   TABLE DATA           D   COPY public.realm_enabled_event_types (realm_id, value) FROM stdin;
    public          hospital    false    250   '&      A          0    16468    realm_events_listeners 
   TABLE DATA           A   COPY public.realm_events_listeners (realm_id, value) FROM stdin;
    public          hospital    false    226   D&      �          0    17919    realm_localizations 
   TABLE DATA           F   COPY public.realm_localizations (realm_id, locale, texts) FROM stdin;
    public          hospital    false    306   �&      B          0    16471    realm_required_credential 
   TABLE DATA           ^   COPY public.realm_required_credential (type, form_label, input, secret, realm_id) FROM stdin;
    public          hospital    false    227   �&      C          0    16478    realm_smtp_config 
   TABLE DATA           B   COPY public.realm_smtp_config (realm_id, value, name) FROM stdin;
    public          hospital    false    228   ='      W          0    16829    realm_supported_locales 
   TABLE DATA           B   COPY public.realm_supported_locales (realm_id, value) FROM stdin;
    public          hospital    false    248   O(      D          0    16488    redirect_uris 
   TABLE DATA           9   COPY public.redirect_uris (client_id, value) FROM stdin;
    public          hospital    false    229   �(      i          0    17152    required_action_config 
   TABLE DATA           Q   COPY public.required_action_config (required_action_id, value, name) FROM stdin;
    public          hospital    false    266   �)      h          0    17145    required_action_provider 
   TABLE DATA           }   COPY public.required_action_provider (id, alias, name, realm_id, enabled, default_action, provider_id, priority) FROM stdin;
    public          hospital    false    265   �)      �          0    17850    resource_attribute 
   TABLE DATA           J   COPY public.resource_attribute (id, name, value, resource_id) FROM stdin;
    public          hospital    false    303    -      z          0    17434    resource_policy 
   TABLE DATA           A   COPY public.resource_policy (resource_id, policy_id) FROM stdin;
    public          hospital    false    283   =-      y          0    17419    resource_scope 
   TABLE DATA           ?   COPY public.resource_scope (resource_id, scope_id) FROM stdin;
    public          hospital    false    282   Z-      t          0    17357    resource_server 
   TABLE DATA           k   COPY public.resource_server (id, allow_rs_remote_mgmt, policy_enforce_mode, decision_strategy) FROM stdin;
    public          hospital    false    277   w-      �          0    17826    resource_server_perm_ticket 
   TABLE DATA           �   COPY public.resource_server_perm_ticket (id, owner, requester, created_timestamp, granted_timestamp, resource_id, scope_id, resource_server_id, policy_id) FROM stdin;
    public          hospital    false    302   �-      w          0    17393    resource_server_policy 
   TABLE DATA           �   COPY public.resource_server_policy (id, name, description, type, decision_strategy, logic, resource_server_id, owner) FROM stdin;
    public          hospital    false    280   �-      u          0    17365    resource_server_resource 
   TABLE DATA           �   COPY public.resource_server_resource (id, name, type, icon_uri, owner, resource_server_id, owner_managed_access, display_name) FROM stdin;
    public          hospital    false    278   �-      v          0    17379    resource_server_scope 
   TABLE DATA           e   COPY public.resource_server_scope (id, name, icon_uri, resource_server_id, display_name) FROM stdin;
    public          hospital    false    279   �-      �          0    17868    resource_uris 
   TABLE DATA           ;   COPY public.resource_uris (resource_id, value) FROM stdin;
    public          hospital    false    304   .      �          0    17878    role_attribute 
   TABLE DATA           B   COPY public.role_attribute (id, role_id, name, value) FROM stdin;
    public          hospital    false    305   %.      E          0    16491    scope_mapping 
   TABLE DATA           ;   COPY public.scope_mapping (client_id, role_id) FROM stdin;
    public          hospital    false    230   B.      {          0    17449    scope_policy 
   TABLE DATA           ;   COPY public.scope_policy (scope_id, policy_id) FROM stdin;
    public          hospital    false    284   �.      G          0    16497    user_attribute 
   TABLE DATA           B   COPY public.user_attribute (name, value, user_id, id) FROM stdin;
    public          hospital    false    232   /      ]          0    16934    user_consent 
   TABLE DATA           �   COPY public.user_consent (id, client_id, user_id, created_date, last_updated_date, client_storage_provider, external_client_id) FROM stdin;
    public          hospital    false    254   q/      �          0    17801    user_consent_client_scope 
   TABLE DATA           N   COPY public.user_consent_client_scope (user_consent_id, scope_id) FROM stdin;
    public          hospital    false    300   �/      H          0    16502    user_entity 
   TABLE DATA           �   COPY public.user_entity (id, email, email_constraint, email_verified, enabled, federation_link, first_name, last_name, realm_id, username, created_timestamp, service_account_client_link, not_before) FROM stdin;
    public          hospital    false    233   �/      I          0    16510    user_federation_config 
   TABLE DATA           Z   COPY public.user_federation_config (user_federation_provider_id, value, name) FROM stdin;
    public          hospital    false    234   �0      d          0    17046    user_federation_mapper 
   TABLE DATA           t   COPY public.user_federation_mapper (id, name, federation_provider_id, federation_mapper_type, realm_id) FROM stdin;
    public          hospital    false    261   �0      e          0    17051    user_federation_mapper_config 
   TABLE DATA           _   COPY public.user_federation_mapper_config (user_federation_mapper_id, value, name) FROM stdin;
    public          hospital    false    262   �0      J          0    16515    user_federation_provider 
   TABLE DATA           �   COPY public.user_federation_provider (id, changed_sync_period, display_name, full_sync_period, last_sync, priority, provider_name, realm_id) FROM stdin;
    public          hospital    false    235   �0      o          0    17214    user_group_membership 
   TABLE DATA           B   COPY public.user_group_membership (group_id, user_id) FROM stdin;
    public          hospital    false    272   1      K          0    16520    user_required_action 
   TABLE DATA           H   COPY public.user_required_action (user_id, required_action) FROM stdin;
    public          hospital    false    236   "1      L          0    16523    user_role_mapping 
   TABLE DATA           =   COPY public.user_role_mapping (role_id, user_id) FROM stdin;
    public          hospital    false    237   ?1      M          0    16526    user_session 
   TABLE DATA           �   COPY public.user_session (id, auth_method, ip_address, last_session_refresh, login_username, realm_id, remember_me, started, user_id, user_session_state, broker_session_id, broker_user_id) FROM stdin;
    public          hospital    false    238   c3      X          0    16832    user_session_note 
   TABLE DATA           F   COPY public.user_session_note (user_session, name, value) FROM stdin;
    public          hospital    false    249   �3      F          0    16494    username_login_failure 
   TABLE DATA           �   COPY public.username_login_failure (realm_id, username, failed_login_not_before, last_failure, last_ip_failure, num_failures) FROM stdin;
    public          hospital    false    231   �3      N          0    16537    web_origins 
   TABLE DATA           7   COPY public.web_origins (client_id, value) FROM stdin;
    public          hospital    false    239   �3      j           2606    17593 &   username_login_failure CONSTRAINT_17-2 
   CONSTRAINT     v   ALTER TABLE ONLY public.username_login_failure
    ADD CONSTRAINT "CONSTRAINT_17-2" PRIMARY KEY (realm_id, username);
 R   ALTER TABLE ONLY public.username_login_failure DROP CONSTRAINT "CONSTRAINT_17-2";
       public            hospital    false    231    231            O           2606    17902 ,   keycloak_role UK_J3RWUVD56ONTGSUHOGM184WW2-2 
   CONSTRAINT     �   ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT "UK_J3RWUVD56ONTGSUHOGM184WW2-2" UNIQUE (name, client_realm_constraint);
 X   ALTER TABLE ONLY public.keycloak_role DROP CONSTRAINT "UK_J3RWUVD56ONTGSUHOGM184WW2-2";
       public            hospital    false    223    223            B           2606    17732 )   client_auth_flow_bindings c_cli_flow_bind 
   CONSTRAINT     |   ALTER TABLE ONLY public.client_auth_flow_bindings
    ADD CONSTRAINT c_cli_flow_bind PRIMARY KEY (client_id, binding_name);
 S   ALTER TABLE ONLY public.client_auth_flow_bindings DROP CONSTRAINT c_cli_flow_bind;
       public            hospital    false    297    297            D           2606    17931 $   client_scope_client c_cli_scope_bind 
   CONSTRAINT     s   ALTER TABLE ONLY public.client_scope_client
    ADD CONSTRAINT c_cli_scope_bind PRIMARY KEY (client_id, scope_id);
 N   ALTER TABLE ONLY public.client_scope_client DROP CONSTRAINT c_cli_scope_bind;
       public            hospital    false    298    298            ?           2606    17607 .   client_initial_access cnstr_client_init_acc_pk 
   CONSTRAINT     l   ALTER TABLE ONLY public.client_initial_access
    ADD CONSTRAINT cnstr_client_init_acc_pk PRIMARY KEY (id);
 X   ALTER TABLE ONLY public.client_initial_access DROP CONSTRAINT cnstr_client_init_acc_pk;
       public            hospital    false    296            �           2606    17255 ,   realm_default_groups con_group_id_def_groups 
   CONSTRAINT     k   ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT con_group_id_def_groups UNIQUE (group_id);
 V   ALTER TABLE ONLY public.realm_default_groups DROP CONSTRAINT con_group_id_def_groups;
       public            hospital    false    273                       2606    17530 !   broker_link constr_broker_link_pk 
   CONSTRAINT     w   ALTER TABLE ONLY public.broker_link
    ADD CONSTRAINT constr_broker_link_pk PRIMARY KEY (identity_provider, user_id);
 K   ALTER TABLE ONLY public.broker_link DROP CONSTRAINT constr_broker_link_pk;
       public            hospital    false    286    286            �           2606    17164 /   client_user_session_note constr_cl_usr_ses_note 
   CONSTRAINT        ALTER TABLE ONLY public.client_user_session_note
    ADD CONSTRAINT constr_cl_usr_ses_note PRIMARY KEY (client_session, name);
 Y   ALTER TABLE ONLY public.client_user_session_note DROP CONSTRAINT constr_cl_usr_ses_note;
       public            hospital    false    264    264            6           2606    17550 +   component_config constr_component_config_pk 
   CONSTRAINT     i   ALTER TABLE ONLY public.component_config
    ADD CONSTRAINT constr_component_config_pk PRIMARY KEY (id);
 U   ALTER TABLE ONLY public.component_config DROP CONSTRAINT constr_component_config_pk;
       public            hospital    false    293            9           2606    17548    component constr_component_pk 
   CONSTRAINT     [   ALTER TABLE ONLY public.component
    ADD CONSTRAINT constr_component_pk PRIMARY KEY (id);
 G   ALTER TABLE ONLY public.component DROP CONSTRAINT constr_component_pk;
       public            hospital    false    294            .           2606    17546 3   fed_user_required_action constr_fed_required_action 
   CONSTRAINT     �   ALTER TABLE ONLY public.fed_user_required_action
    ADD CONSTRAINT constr_fed_required_action PRIMARY KEY (required_action, user_id);
 ]   ALTER TABLE ONLY public.fed_user_required_action DROP CONSTRAINT constr_fed_required_action;
       public            hospital    false    291    291                       2606    17532 *   fed_user_attribute constr_fed_user_attr_pk 
   CONSTRAINT     h   ALTER TABLE ONLY public.fed_user_attribute
    ADD CONSTRAINT constr_fed_user_attr_pk PRIMARY KEY (id);
 T   ALTER TABLE ONLY public.fed_user_attribute DROP CONSTRAINT constr_fed_user_attr_pk;
       public            hospital    false    287            !           2606    17534 +   fed_user_consent constr_fed_user_consent_pk 
   CONSTRAINT     i   ALTER TABLE ONLY public.fed_user_consent
    ADD CONSTRAINT constr_fed_user_consent_pk PRIMARY KEY (id);
 U   ALTER TABLE ONLY public.fed_user_consent DROP CONSTRAINT constr_fed_user_consent_pk;
       public            hospital    false    288            &           2606    17540 +   fed_user_credential constr_fed_user_cred_pk 
   CONSTRAINT     i   ALTER TABLE ONLY public.fed_user_credential
    ADD CONSTRAINT constr_fed_user_cred_pk PRIMARY KEY (id);
 U   ALTER TABLE ONLY public.fed_user_credential DROP CONSTRAINT constr_fed_user_cred_pk;
       public            hospital    false    289            *           2606    17542 /   fed_user_group_membership constr_fed_user_group 
   CONSTRAINT     |   ALTER TABLE ONLY public.fed_user_group_membership
    ADD CONSTRAINT constr_fed_user_group PRIMARY KEY (group_id, user_id);
 Y   ALTER TABLE ONLY public.fed_user_group_membership DROP CONSTRAINT constr_fed_user_group;
       public            hospital    false    290    290            2           2606    17544 *   fed_user_role_mapping constr_fed_user_role 
   CONSTRAINT     v   ALTER TABLE ONLY public.fed_user_role_mapping
    ADD CONSTRAINT constr_fed_user_role PRIMARY KEY (role_id, user_id);
 T   ALTER TABLE ONLY public.fed_user_role_mapping DROP CONSTRAINT constr_fed_user_role;
       public            hospital    false    292    292            =           2606    17587 $   federated_user constr_federated_user 
   CONSTRAINT     b   ALTER TABLE ONLY public.federated_user
    ADD CONSTRAINT constr_federated_user PRIMARY KEY (id);
 N   ALTER TABLE ONLY public.federated_user DROP CONSTRAINT constr_federated_user;
       public            hospital    false    295            �           2606    17691 0   realm_default_groups constr_realm_default_groups 
   CONSTRAINT     ~   ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT constr_realm_default_groups PRIMARY KEY (realm_id, group_id);
 Z   ALTER TABLE ONLY public.realm_default_groups DROP CONSTRAINT constr_realm_default_groups;
       public            hospital    false    273    273            �           2606    17708 8   realm_enabled_event_types constr_realm_enabl_event_types 
   CONSTRAINT     �   ALTER TABLE ONLY public.realm_enabled_event_types
    ADD CONSTRAINT constr_realm_enabl_event_types PRIMARY KEY (realm_id, value);
 b   ALTER TABLE ONLY public.realm_enabled_event_types DROP CONSTRAINT constr_realm_enabl_event_types;
       public            hospital    false    250    250            ]           2606    17710 4   realm_events_listeners constr_realm_events_listeners 
   CONSTRAINT        ALTER TABLE ONLY public.realm_events_listeners
    ADD CONSTRAINT constr_realm_events_listeners PRIMARY KEY (realm_id, value);
 ^   ALTER TABLE ONLY public.realm_events_listeners DROP CONSTRAINT constr_realm_events_listeners;
       public            hospital    false    226    226            �           2606    17712 6   realm_supported_locales constr_realm_supported_locales 
   CONSTRAINT     �   ALTER TABLE ONLY public.realm_supported_locales
    ADD CONSTRAINT constr_realm_supported_locales PRIMARY KEY (realm_id, value);
 `   ALTER TABLE ONLY public.realm_supported_locales DROP CONSTRAINT constr_realm_supported_locales;
       public            hospital    false    248    248            �           2606    16841    identity_provider constraint_2b 
   CONSTRAINT     f   ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT constraint_2b PRIMARY KEY (internal_id);
 I   ALTER TABLE ONLY public.identity_provider DROP CONSTRAINT constraint_2b;
       public            hospital    false    246            �           2606    16775    client_attributes constraint_3c 
   CONSTRAINT     j   ALTER TABLE ONLY public.client_attributes
    ADD CONSTRAINT constraint_3c PRIMARY KEY (client_id, name);
 I   ALTER TABLE ONLY public.client_attributes DROP CONSTRAINT constraint_3c;
       public            hospital    false    240    240            L           2606    16549    event_entity constraint_4 
   CONSTRAINT     W   ALTER TABLE ONLY public.event_entity
    ADD CONSTRAINT constraint_4 PRIMARY KEY (id);
 C   ALTER TABLE ONLY public.event_entity DROP CONSTRAINT constraint_4;
       public            hospital    false    222            �           2606    16843     federated_identity constraint_40 
   CONSTRAINT     v   ALTER TABLE ONLY public.federated_identity
    ADD CONSTRAINT constraint_40 PRIMARY KEY (identity_provider, user_id);
 J   ALTER TABLE ONLY public.federated_identity DROP CONSTRAINT constraint_40;
       public            hospital    false    245    245            U           2606    16551    realm constraint_4a 
   CONSTRAINT     Q   ALTER TABLE ONLY public.realm
    ADD CONSTRAINT constraint_4a PRIMARY KEY (id);
 =   ALTER TABLE ONLY public.realm DROP CONSTRAINT constraint_4a;
       public            hospital    false    224            C           2606    16553     client_session_role constraint_5 
   CONSTRAINT     s   ALTER TABLE ONLY public.client_session_role
    ADD CONSTRAINT constraint_5 PRIMARY KEY (client_session, role_id);
 J   ALTER TABLE ONLY public.client_session_role DROP CONSTRAINT constraint_5;
       public            hospital    false    219    219            �           2606    16555    user_session constraint_57 
   CONSTRAINT     X   ALTER TABLE ONLY public.user_session
    ADD CONSTRAINT constraint_57 PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.user_session DROP CONSTRAINT constraint_57;
       public            hospital    false    238            z           2606    16557 &   user_federation_provider constraint_5c 
   CONSTRAINT     d   ALTER TABLE ONLY public.user_federation_provider
    ADD CONSTRAINT constraint_5c PRIMARY KEY (id);
 P   ALTER TABLE ONLY public.user_federation_provider DROP CONSTRAINT constraint_5c;
       public            hospital    false    235            �           2606    16777 !   client_session_note constraint_5e 
   CONSTRAINT     q   ALTER TABLE ONLY public.client_session_note
    ADD CONSTRAINT constraint_5e PRIMARY KEY (client_session, name);
 K   ALTER TABLE ONLY public.client_session_note DROP CONSTRAINT constraint_5e;
       public            hospital    false    241    241            ;           2606    16561    client constraint_7 
   CONSTRAINT     Q   ALTER TABLE ONLY public.client
    ADD CONSTRAINT constraint_7 PRIMARY KEY (id);
 =   ALTER TABLE ONLY public.client DROP CONSTRAINT constraint_7;
       public            hospital    false    217            @           2606    16563    client_session constraint_8 
   CONSTRAINT     Y   ALTER TABLE ONLY public.client_session
    ADD CONSTRAINT constraint_8 PRIMARY KEY (id);
 E   ALTER TABLE ONLY public.client_session DROP CONSTRAINT constraint_8;
       public            hospital    false    218            g           2606    16565    scope_mapping constraint_81 
   CONSTRAINT     i   ALTER TABLE ONLY public.scope_mapping
    ADD CONSTRAINT constraint_81 PRIMARY KEY (client_id, role_id);
 E   ALTER TABLE ONLY public.scope_mapping DROP CONSTRAINT constraint_81;
       public            hospital    false    230    230            �           2606    16779 '   client_node_registrations constraint_84 
   CONSTRAINT     r   ALTER TABLE ONLY public.client_node_registrations
    ADD CONSTRAINT constraint_84 PRIMARY KEY (client_id, name);
 Q   ALTER TABLE ONLY public.client_node_registrations DROP CONSTRAINT constraint_84;
       public            hospital    false    242    242            Z           2606    16567    realm_attribute constraint_9 
   CONSTRAINT     f   ALTER TABLE ONLY public.realm_attribute
    ADD CONSTRAINT constraint_9 PRIMARY KEY (name, realm_id);
 F   ALTER TABLE ONLY public.realm_attribute DROP CONSTRAINT constraint_9;
       public            hospital    false    225    225            `           2606    16569 '   realm_required_credential constraint_92 
   CONSTRAINT     q   ALTER TABLE ONLY public.realm_required_credential
    ADD CONSTRAINT constraint_92 PRIMARY KEY (realm_id, type);
 Q   ALTER TABLE ONLY public.realm_required_credential DROP CONSTRAINT constraint_92;
       public            hospital    false    227    227            Q           2606    16571    keycloak_role constraint_a 
   CONSTRAINT     X   ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT constraint_a PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.keycloak_role DROP CONSTRAINT constraint_a;
       public            hospital    false    223            �           2606    17695 0   admin_event_entity constraint_admin_event_entity 
   CONSTRAINT     n   ALTER TABLE ONLY public.admin_event_entity
    ADD CONSTRAINT constraint_admin_event_entity PRIMARY KEY (id);
 Z   ALTER TABLE ONLY public.admin_event_entity DROP CONSTRAINT constraint_admin_event_entity;
       public            hospital    false    256            �           2606    17072 1   authenticator_config_entry constraint_auth_cfg_pk 
   CONSTRAINT     �   ALTER TABLE ONLY public.authenticator_config_entry
    ADD CONSTRAINT constraint_auth_cfg_pk PRIMARY KEY (authenticator_id, name);
 [   ALTER TABLE ONLY public.authenticator_config_entry DROP CONSTRAINT constraint_auth_cfg_pk;
       public            hospital    false    260    260            �           2606    17070 0   authentication_execution constraint_auth_exec_pk 
   CONSTRAINT     n   ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT constraint_auth_exec_pk PRIMARY KEY (id);
 Z   ALTER TABLE ONLY public.authentication_execution DROP CONSTRAINT constraint_auth_exec_pk;
       public            hospital    false    259            �           2606    17068 +   authentication_flow constraint_auth_flow_pk 
   CONSTRAINT     i   ALTER TABLE ONLY public.authentication_flow
    ADD CONSTRAINT constraint_auth_flow_pk PRIMARY KEY (id);
 U   ALTER TABLE ONLY public.authentication_flow DROP CONSTRAINT constraint_auth_flow_pk;
       public            hospital    false    258            �           2606    17066 '   authenticator_config constraint_auth_pk 
   CONSTRAINT     e   ALTER TABLE ONLY public.authenticator_config
    ADD CONSTRAINT constraint_auth_pk PRIMARY KEY (id);
 Q   ALTER TABLE ONLY public.authenticator_config DROP CONSTRAINT constraint_auth_pk;
       public            hospital    false    257            �           2606    17076 4   client_session_auth_status constraint_auth_status_pk 
   CONSTRAINT     �   ALTER TABLE ONLY public.client_session_auth_status
    ADD CONSTRAINT constraint_auth_status_pk PRIMARY KEY (client_session, authenticator);
 ^   ALTER TABLE ONLY public.client_session_auth_status DROP CONSTRAINT constraint_auth_status_pk;
       public            hospital    false    263    263            �           2606    16573    user_role_mapping constraint_c 
   CONSTRAINT     j   ALTER TABLE ONLY public.user_role_mapping
    ADD CONSTRAINT constraint_c PRIMARY KEY (role_id, user_id);
 H   ALTER TABLE ONLY public.user_role_mapping DROP CONSTRAINT constraint_c;
       public            hospital    false    237    237            E           2606    17689 (   composite_role constraint_composite_role 
   CONSTRAINT     y   ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT constraint_composite_role PRIMARY KEY (composite, child_role);
 R   ALTER TABLE ONLY public.composite_role DROP CONSTRAINT constraint_composite_role;
       public            hospital    false    220    220            �           2606    16959 /   client_session_prot_mapper constraint_cs_pmp_pk 
   CONSTRAINT     �   ALTER TABLE ONLY public.client_session_prot_mapper
    ADD CONSTRAINT constraint_cs_pmp_pk PRIMARY KEY (client_session, protocol_mapper_id);
 Y   ALTER TABLE ONLY public.client_session_prot_mapper DROP CONSTRAINT constraint_cs_pmp_pk;
       public            hospital    false    255    255            �           2606    16845 %   identity_provider_config constraint_d 
   CONSTRAINT     {   ALTER TABLE ONLY public.identity_provider_config
    ADD CONSTRAINT constraint_d PRIMARY KEY (identity_provider_id, name);
 O   ALTER TABLE ONLY public.identity_provider_config DROP CONSTRAINT constraint_d;
       public            hospital    false    247    247                       2606    17413    policy_config constraint_dpc 
   CONSTRAINT     g   ALTER TABLE ONLY public.policy_config
    ADD CONSTRAINT constraint_dpc PRIMARY KEY (policy_id, name);
 F   ALTER TABLE ONLY public.policy_config DROP CONSTRAINT constraint_dpc;
       public            hospital    false    281    281            b           2606    16575    realm_smtp_config constraint_e 
   CONSTRAINT     h   ALTER TABLE ONLY public.realm_smtp_config
    ADD CONSTRAINT constraint_e PRIMARY KEY (realm_id, name);
 H   ALTER TABLE ONLY public.realm_smtp_config DROP CONSTRAINT constraint_e;
       public            hospital    false    228    228            I           2606    16577    credential constraint_f 
   CONSTRAINT     U   ALTER TABLE ONLY public.credential
    ADD CONSTRAINT constraint_f PRIMARY KEY (id);
 A   ALTER TABLE ONLY public.credential DROP CONSTRAINT constraint_f;
       public            hospital    false    221            x           2606    16579 $   user_federation_config constraint_f9 
   CONSTRAINT     �   ALTER TABLE ONLY public.user_federation_config
    ADD CONSTRAINT constraint_f9 PRIMARY KEY (user_federation_provider_id, name);
 N   ALTER TABLE ONLY public.user_federation_config DROP CONSTRAINT constraint_f9;
       public            hospital    false    234    234            Q           2606    17830 ,   resource_server_perm_ticket constraint_fapmt 
   CONSTRAINT     j   ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT constraint_fapmt PRIMARY KEY (id);
 V   ALTER TABLE ONLY public.resource_server_perm_ticket DROP CONSTRAINT constraint_fapmt;
       public            hospital    false    302            �           2606    17371 )   resource_server_resource constraint_farsr 
   CONSTRAINT     g   ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT constraint_farsr PRIMARY KEY (id);
 S   ALTER TABLE ONLY public.resource_server_resource DROP CONSTRAINT constraint_farsr;
       public            hospital    false    278            	           2606    17399 (   resource_server_policy constraint_farsrp 
   CONSTRAINT     f   ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT constraint_farsrp PRIMARY KEY (id);
 R   ALTER TABLE ONLY public.resource_server_policy DROP CONSTRAINT constraint_farsrp;
       public            hospital    false    280                       2606    17468 %   associated_policy constraint_farsrpap 
   CONSTRAINT     �   ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT constraint_farsrpap PRIMARY KEY (policy_id, associated_policy_id);
 O   ALTER TABLE ONLY public.associated_policy DROP CONSTRAINT constraint_farsrpap;
       public            hospital    false    285    285                       2606    17438 "   resource_policy constraint_farsrpp 
   CONSTRAINT     t   ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT constraint_farsrpp PRIMARY KEY (resource_id, policy_id);
 L   ALTER TABLE ONLY public.resource_policy DROP CONSTRAINT constraint_farsrpp;
       public            hospital    false    283    283                       2606    17385 '   resource_server_scope constraint_farsrs 
   CONSTRAINT     e   ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT constraint_farsrs PRIMARY KEY (id);
 Q   ALTER TABLE ONLY public.resource_server_scope DROP CONSTRAINT constraint_farsrs;
       public            hospital    false    279                       2606    17423 !   resource_scope constraint_farsrsp 
   CONSTRAINT     r   ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT constraint_farsrsp PRIMARY KEY (resource_id, scope_id);
 K   ALTER TABLE ONLY public.resource_scope DROP CONSTRAINT constraint_farsrsp;
       public            hospital    false    282    282                       2606    17453     scope_policy constraint_farsrsps 
   CONSTRAINT     o   ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT constraint_farsrsps PRIMARY KEY (scope_id, policy_id);
 J   ALTER TABLE ONLY public.scope_policy DROP CONSTRAINT constraint_farsrsps;
       public            hospital    false    284    284            p           2606    16581    user_entity constraint_fb 
   CONSTRAINT     W   ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT constraint_fb PRIMARY KEY (id);
 C   ALTER TABLE ONLY public.user_entity DROP CONSTRAINT constraint_fb;
       public            hospital    false    233            �           2606    17080 9   user_federation_mapper_config constraint_fedmapper_cfg_pm 
   CONSTRAINT     �   ALTER TABLE ONLY public.user_federation_mapper_config
    ADD CONSTRAINT constraint_fedmapper_cfg_pm PRIMARY KEY (user_federation_mapper_id, name);
 c   ALTER TABLE ONLY public.user_federation_mapper_config DROP CONSTRAINT constraint_fedmapper_cfg_pm;
       public            hospital    false    262    262            �           2606    17078 -   user_federation_mapper constraint_fedmapperpm 
   CONSTRAINT     k   ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT constraint_fedmapperpm PRIMARY KEY (id);
 W   ALTER TABLE ONLY public.user_federation_mapper DROP CONSTRAINT constraint_fedmapperpm;
       public            hospital    false    261            O           2606    17815 6   fed_user_consent_cl_scope constraint_fgrntcsnt_clsc_pm 
   CONSTRAINT     �   ALTER TABLE ONLY public.fed_user_consent_cl_scope
    ADD CONSTRAINT constraint_fgrntcsnt_clsc_pm PRIMARY KEY (user_consent_id, scope_id);
 `   ALTER TABLE ONLY public.fed_user_consent_cl_scope DROP CONSTRAINT constraint_fgrntcsnt_clsc_pm;
       public            hospital    false    301    301            L           2606    17805 5   user_consent_client_scope constraint_grntcsnt_clsc_pm 
   CONSTRAINT     �   ALTER TABLE ONLY public.user_consent_client_scope
    ADD CONSTRAINT constraint_grntcsnt_clsc_pm PRIMARY KEY (user_consent_id, scope_id);
 _   ALTER TABLE ONLY public.user_consent_client_scope DROP CONSTRAINT constraint_grntcsnt_clsc_pm;
       public            hospital    false    300    300            �           2606    16953 #   user_consent constraint_grntcsnt_pm 
   CONSTRAINT     a   ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT constraint_grntcsnt_pm PRIMARY KEY (id);
 M   ALTER TABLE ONLY public.user_consent DROP CONSTRAINT constraint_grntcsnt_pm;
       public            hospital    false    254            �           2606    17222    keycloak_group constraint_group 
   CONSTRAINT     ]   ALTER TABLE ONLY public.keycloak_group
    ADD CONSTRAINT constraint_group PRIMARY KEY (id);
 I   ALTER TABLE ONLY public.keycloak_group DROP CONSTRAINT constraint_group;
       public            hospital    false    269            �           2606    17229 -   group_attribute constraint_group_attribute_pk 
   CONSTRAINT     k   ALTER TABLE ONLY public.group_attribute
    ADD CONSTRAINT constraint_group_attribute_pk PRIMARY KEY (id);
 W   ALTER TABLE ONLY public.group_attribute DROP CONSTRAINT constraint_group_attribute_pk;
       public            hospital    false    271            �           2606    17243 (   group_role_mapping constraint_group_role 
   CONSTRAINT     u   ALTER TABLE ONLY public.group_role_mapping
    ADD CONSTRAINT constraint_group_role PRIMARY KEY (role_id, group_id);
 R   ALTER TABLE ONLY public.group_role_mapping DROP CONSTRAINT constraint_group_role;
       public            hospital    false    270    270            �           2606    16949 (   identity_provider_mapper constraint_idpm 
   CONSTRAINT     f   ALTER TABLE ONLY public.identity_provider_mapper
    ADD CONSTRAINT constraint_idpm PRIMARY KEY (id);
 R   ALTER TABLE ONLY public.identity_provider_mapper DROP CONSTRAINT constraint_idpm;
       public            hospital    false    252            �           2606    17129 '   idp_mapper_config constraint_idpmconfig 
   CONSTRAINT     v   ALTER TABLE ONLY public.idp_mapper_config
    ADD CONSTRAINT constraint_idpmconfig PRIMARY KEY (idp_mapper_id, name);
 Q   ALTER TABLE ONLY public.idp_mapper_config DROP CONSTRAINT constraint_idpmconfig;
       public            hospital    false    253    253            �           2606    16947 !   migration_model constraint_migmod 
   CONSTRAINT     _   ALTER TABLE ONLY public.migration_model
    ADD CONSTRAINT constraint_migmod PRIMARY KEY (id);
 K   ALTER TABLE ONLY public.migration_model DROP CONSTRAINT constraint_migmod;
       public            hospital    false    251            �           2606    17908 1   offline_client_session constraint_offl_cl_ses_pk3 
   CONSTRAINT     �   ALTER TABLE ONLY public.offline_client_session
    ADD CONSTRAINT constraint_offl_cl_ses_pk3 PRIMARY KEY (user_session_id, client_id, client_storage_provider, external_client_id, offline_flag);
 [   ALTER TABLE ONLY public.offline_client_session DROP CONSTRAINT constraint_offl_cl_ses_pk3;
       public            hospital    false    268    268    268    268    268            �           2606    17199 /   offline_user_session constraint_offl_us_ses_pk2 
   CONSTRAINT     �   ALTER TABLE ONLY public.offline_user_session
    ADD CONSTRAINT constraint_offl_us_ses_pk2 PRIMARY KEY (user_session_id, offline_flag);
 Y   ALTER TABLE ONLY public.offline_user_session DROP CONSTRAINT constraint_offl_us_ses_pk2;
       public            hospital    false    267    267            �           2606    16839    protocol_mapper constraint_pcm 
   CONSTRAINT     \   ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT constraint_pcm PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.protocol_mapper DROP CONSTRAINT constraint_pcm;
       public            hospital    false    243            �           2606    17122 *   protocol_mapper_config constraint_pmconfig 
   CONSTRAINT     ~   ALTER TABLE ONLY public.protocol_mapper_config
    ADD CONSTRAINT constraint_pmconfig PRIMARY KEY (protocol_mapper_id, name);
 T   ALTER TABLE ONLY public.protocol_mapper_config DROP CONSTRAINT constraint_pmconfig;
       public            hospital    false    244    244            d           2606    17714 &   redirect_uris constraint_redirect_uris 
   CONSTRAINT     r   ALTER TABLE ONLY public.redirect_uris
    ADD CONSTRAINT constraint_redirect_uris PRIMARY KEY (client_id, value);
 P   ALTER TABLE ONLY public.redirect_uris DROP CONSTRAINT constraint_redirect_uris;
       public            hospital    false    229    229            �           2606    17162 0   required_action_config constraint_req_act_cfg_pk 
   CONSTRAINT     �   ALTER TABLE ONLY public.required_action_config
    ADD CONSTRAINT constraint_req_act_cfg_pk PRIMARY KEY (required_action_id, name);
 Z   ALTER TABLE ONLY public.required_action_config DROP CONSTRAINT constraint_req_act_cfg_pk;
       public            hospital    false    266    266            �           2606    17160 2   required_action_provider constraint_req_act_prv_pk 
   CONSTRAINT     p   ALTER TABLE ONLY public.required_action_provider
    ADD CONSTRAINT constraint_req_act_prv_pk PRIMARY KEY (id);
 \   ALTER TABLE ONLY public.required_action_provider DROP CONSTRAINT constraint_req_act_prv_pk;
       public            hospital    false    265            }           2606    17074 /   user_required_action constraint_required_action 
   CONSTRAINT     �   ALTER TABLE ONLY public.user_required_action
    ADD CONSTRAINT constraint_required_action PRIMARY KEY (required_action, user_id);
 Y   ALTER TABLE ONLY public.user_required_action DROP CONSTRAINT constraint_required_action;
       public            hospital    false    236    236            W           2606    17877 '   resource_uris constraint_resour_uris_pk 
   CONSTRAINT     u   ALTER TABLE ONLY public.resource_uris
    ADD CONSTRAINT constraint_resour_uris_pk PRIMARY KEY (resource_id, value);
 Q   ALTER TABLE ONLY public.resource_uris DROP CONSTRAINT constraint_resour_uris_pk;
       public            hospital    false    304    304            Y           2606    17884 +   role_attribute constraint_role_attribute_pk 
   CONSTRAINT     i   ALTER TABLE ONLY public.role_attribute
    ADD CONSTRAINT constraint_role_attribute_pk PRIMARY KEY (id);
 U   ALTER TABLE ONLY public.role_attribute DROP CONSTRAINT constraint_role_attribute_pk;
       public            hospital    false    305            l           2606    17158 +   user_attribute constraint_user_attribute_pk 
   CONSTRAINT     i   ALTER TABLE ONLY public.user_attribute
    ADD CONSTRAINT constraint_user_attribute_pk PRIMARY KEY (id);
 U   ALTER TABLE ONLY public.user_attribute DROP CONSTRAINT constraint_user_attribute_pk;
       public            hospital    false    232            �           2606    17236 +   user_group_membership constraint_user_group 
   CONSTRAINT     x   ALTER TABLE ONLY public.user_group_membership
    ADD CONSTRAINT constraint_user_group PRIMARY KEY (group_id, user_id);
 U   ALTER TABLE ONLY public.user_group_membership DROP CONSTRAINT constraint_user_group;
       public            hospital    false    272    272            �           2606    16849 #   user_session_note constraint_usn_pk 
   CONSTRAINT     q   ALTER TABLE ONLY public.user_session_note
    ADD CONSTRAINT constraint_usn_pk PRIMARY KEY (user_session, name);
 M   ALTER TABLE ONLY public.user_session_note DROP CONSTRAINT constraint_usn_pk;
       public            hospital    false    249    249            �           2606    17716 "   web_origins constraint_web_origins 
   CONSTRAINT     n   ALTER TABLE ONLY public.web_origins
    ADD CONSTRAINT constraint_web_origins PRIMARY KEY (client_id, value);
 L   ALTER TABLE ONLY public.web_origins DROP CONSTRAINT constraint_web_origins;
       public            hospital    false    239    239            9           2606    16393 0   databasechangeloglock databasechangeloglock_pkey 
   CONSTRAINT     n   ALTER TABLE ONLY public.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);
 Z   ALTER TABLE ONLY public.databasechangeloglock DROP CONSTRAINT databasechangeloglock_pkey;
       public            hospital    false    215            �           2606    17339 '   client_scope_attributes pk_cl_tmpl_attr 
   CONSTRAINT     q   ALTER TABLE ONLY public.client_scope_attributes
    ADD CONSTRAINT pk_cl_tmpl_attr PRIMARY KEY (scope_id, name);
 Q   ALTER TABLE ONLY public.client_scope_attributes DROP CONSTRAINT pk_cl_tmpl_attr;
       public            hospital    false    275    275            �           2606    17298    client_scope pk_cli_template 
   CONSTRAINT     Z   ALTER TABLE ONLY public.client_scope
    ADD CONSTRAINT pk_cli_template PRIMARY KEY (id);
 F   ALTER TABLE ONLY public.client_scope DROP CONSTRAINT pk_cli_template;
       public            hospital    false    274            �           2606    17669 "   resource_server pk_resource_server 
   CONSTRAINT     `   ALTER TABLE ONLY public.resource_server
    ADD CONSTRAINT pk_resource_server PRIMARY KEY (id);
 L   ALTER TABLE ONLY public.resource_server DROP CONSTRAINT pk_resource_server;
       public            hospital    false    277            �           2606    17327 +   client_scope_role_mapping pk_template_scope 
   CONSTRAINT     x   ALTER TABLE ONLY public.client_scope_role_mapping
    ADD CONSTRAINT pk_template_scope PRIMARY KEY (scope_id, role_id);
 U   ALTER TABLE ONLY public.client_scope_role_mapping DROP CONSTRAINT pk_template_scope;
       public            hospital    false    276    276            J           2606    17790 )   default_client_scope r_def_cli_scope_bind 
   CONSTRAINT     w   ALTER TABLE ONLY public.default_client_scope
    ADD CONSTRAINT r_def_cli_scope_bind PRIMARY KEY (realm_id, scope_id);
 S   ALTER TABLE ONLY public.default_client_scope DROP CONSTRAINT r_def_cli_scope_bind;
       public            hospital    false    299    299            \           2606    17925 ,   realm_localizations realm_localizations_pkey 
   CONSTRAINT     x   ALTER TABLE ONLY public.realm_localizations
    ADD CONSTRAINT realm_localizations_pkey PRIMARY KEY (realm_id, locale);
 V   ALTER TABLE ONLY public.realm_localizations DROP CONSTRAINT realm_localizations_pkey;
       public            hospital    false    306    306            U           2606    17857    resource_attribute res_attr_pk 
   CONSTRAINT     \   ALTER TABLE ONLY public.resource_attribute
    ADD CONSTRAINT res_attr_pk PRIMARY KEY (id);
 H   ALTER TABLE ONLY public.resource_attribute DROP CONSTRAINT res_attr_pk;
       public            hospital    false    303            �           2606    17599    keycloak_group sibling_names 
   CONSTRAINT     o   ALTER TABLE ONLY public.keycloak_group
    ADD CONSTRAINT sibling_names UNIQUE (realm_id, parent_group, name);
 F   ALTER TABLE ONLY public.keycloak_group DROP CONSTRAINT sibling_names;
       public            hospital    false    269    269    269            �           2606    16896 /   identity_provider uk_2daelwnibji49avxsrtuf6xj33 
   CONSTRAINT     ~   ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT uk_2daelwnibji49avxsrtuf6xj33 UNIQUE (provider_alias, realm_id);
 Y   ALTER TABLE ONLY public.identity_provider DROP CONSTRAINT uk_2daelwnibji49avxsrtuf6xj33;
       public            hospital    false    246    246            >           2606    16585 #   client uk_b71cjlbenv945rb6gcon438at 
   CONSTRAINT     m   ALTER TABLE ONLY public.client
    ADD CONSTRAINT uk_b71cjlbenv945rb6gcon438at UNIQUE (realm_id, client_id);
 M   ALTER TABLE ONLY public.client DROP CONSTRAINT uk_b71cjlbenv945rb6gcon438at;
       public            hospital    false    217    217            �           2606    17743    client_scope uk_cli_scope 
   CONSTRAINT     ^   ALTER TABLE ONLY public.client_scope
    ADD CONSTRAINT uk_cli_scope UNIQUE (realm_id, name);
 C   ALTER TABLE ONLY public.client_scope DROP CONSTRAINT uk_cli_scope;
       public            hospital    false    274    274            t           2606    16589 (   user_entity uk_dykn684sl8up1crfei6eckhd7 
   CONSTRAINT     y   ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT uk_dykn684sl8up1crfei6eckhd7 UNIQUE (realm_id, email_constraint);
 R   ALTER TABLE ONLY public.user_entity DROP CONSTRAINT uk_dykn684sl8up1crfei6eckhd7;
       public            hospital    false    233    233                       2606    17916 4   resource_server_resource uk_frsr6t700s9v50bu18ws5ha6 
   CONSTRAINT     �   ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT uk_frsr6t700s9v50bu18ws5ha6 UNIQUE (name, owner, resource_server_id);
 ^   ALTER TABLE ONLY public.resource_server_resource DROP CONSTRAINT uk_frsr6t700s9v50bu18ws5ha6;
       public            hospital    false    278    278    278            S           2606    17912 7   resource_server_perm_ticket uk_frsr6t700s9v50bu18ws5pmt 
   CONSTRAINT     �   ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT uk_frsr6t700s9v50bu18ws5pmt UNIQUE (owner, requester, resource_server_id, resource_id, scope_id);
 a   ALTER TABLE ONLY public.resource_server_perm_ticket DROP CONSTRAINT uk_frsr6t700s9v50bu18ws5pmt;
       public            hospital    false    302    302    302    302    302                       2606    17660 2   resource_server_policy uk_frsrpt700s9v50bu18ws5ha6 
   CONSTRAINT     �   ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT uk_frsrpt700s9v50bu18ws5ha6 UNIQUE (name, resource_server_id);
 \   ALTER TABLE ONLY public.resource_server_policy DROP CONSTRAINT uk_frsrpt700s9v50bu18ws5ha6;
       public            hospital    false    280    280                       2606    17664 1   resource_server_scope uk_frsrst700s9v50bu18ws5ha6 
   CONSTRAINT     �   ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT uk_frsrst700s9v50bu18ws5ha6 UNIQUE (name, resource_server_id);
 [   ALTER TABLE ONLY public.resource_server_scope DROP CONSTRAINT uk_frsrst700s9v50bu18ws5ha6;
       public            hospital    false    279    279            �           2606    17904 )   user_consent uk_jkuwuvd56ontgsuhogm8uewrt 
   CONSTRAINT     �   ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT uk_jkuwuvd56ontgsuhogm8uewrt UNIQUE (client_id, client_storage_provider, external_client_id, user_id);
 S   ALTER TABLE ONLY public.user_consent DROP CONSTRAINT uk_jkuwuvd56ontgsuhogm8uewrt;
       public            hospital    false    254    254    254    254            X           2606    16597 "   realm uk_orvsdmla56612eaefiq6wl5oi 
   CONSTRAINT     ]   ALTER TABLE ONLY public.realm
    ADD CONSTRAINT uk_orvsdmla56612eaefiq6wl5oi UNIQUE (name);
 L   ALTER TABLE ONLY public.realm DROP CONSTRAINT uk_orvsdmla56612eaefiq6wl5oi;
       public            hospital    false    224            v           2606    17589 (   user_entity uk_ru8tt6t700s9v50bu18ws5ha6 
   CONSTRAINT     q   ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT uk_ru8tt6t700s9v50bu18ws5ha6 UNIQUE (realm_id, username);
 R   ALTER TABLE ONLY public.user_entity DROP CONSTRAINT uk_ru8tt6t700s9v50bu18ws5ha6;
       public            hospital    false    233    233            �           1259    17941    idx_admin_event_time    INDEX     i   CREATE INDEX idx_admin_event_time ON public.admin_event_entity USING btree (realm_id, admin_event_time);
 (   DROP INDEX public.idx_admin_event_time;
       public            hospital    false    256    256                       1259    17613    idx_assoc_pol_assoc_pol_id    INDEX     h   CREATE INDEX idx_assoc_pol_assoc_pol_id ON public.associated_policy USING btree (associated_policy_id);
 .   DROP INDEX public.idx_assoc_pol_assoc_pol_id;
       public            hospital    false    285            �           1259    17617    idx_auth_config_realm    INDEX     Z   CREATE INDEX idx_auth_config_realm ON public.authenticator_config USING btree (realm_id);
 )   DROP INDEX public.idx_auth_config_realm;
       public            hospital    false    257            �           1259    17615    idx_auth_exec_flow    INDEX     Z   CREATE INDEX idx_auth_exec_flow ON public.authentication_execution USING btree (flow_id);
 &   DROP INDEX public.idx_auth_exec_flow;
       public            hospital    false    259            �           1259    17614    idx_auth_exec_realm_flow    INDEX     j   CREATE INDEX idx_auth_exec_realm_flow ON public.authentication_execution USING btree (realm_id, flow_id);
 ,   DROP INDEX public.idx_auth_exec_realm_flow;
       public            hospital    false    259    259            �           1259    17616    idx_auth_flow_realm    INDEX     W   CREATE INDEX idx_auth_flow_realm ON public.authentication_flow USING btree (realm_id);
 '   DROP INDEX public.idx_auth_flow_realm;
       public            hospital    false    258            E           1259    17932    idx_cl_clscope    INDEX     R   CREATE INDEX idx_cl_clscope ON public.client_scope_client USING btree (scope_id);
 "   DROP INDEX public.idx_cl_clscope;
       public            hospital    false    298            <           1259    17917    idx_client_id    INDEX     E   CREATE INDEX idx_client_id ON public.client USING btree (client_id);
 !   DROP INDEX public.idx_client_id;
       public            hospital    false    217            @           1259    17657    idx_client_init_acc_realm    INDEX     _   CREATE INDEX idx_client_init_acc_realm ON public.client_initial_access USING btree (realm_id);
 -   DROP INDEX public.idx_client_init_acc_realm;
       public            hospital    false    296            A           1259    17621    idx_client_session_session    INDEX     [   CREATE INDEX idx_client_session_session ON public.client_session USING btree (session_id);
 .   DROP INDEX public.idx_client_session_session;
       public            hospital    false    218            �           1259    17820    idx_clscope_attrs    INDEX     Y   CREATE INDEX idx_clscope_attrs ON public.client_scope_attributes USING btree (scope_id);
 %   DROP INDEX public.idx_clscope_attrs;
       public            hospital    false    275            F           1259    17929    idx_clscope_cl    INDEX     S   CREATE INDEX idx_clscope_cl ON public.client_scope_client USING btree (client_id);
 "   DROP INDEX public.idx_clscope_cl;
       public            hospital    false    298            �           1259    17817    idx_clscope_protmap    INDEX     Z   CREATE INDEX idx_clscope_protmap ON public.protocol_mapper USING btree (client_scope_id);
 '   DROP INDEX public.idx_clscope_protmap;
       public            hospital    false    243            �           1259    17818    idx_clscope_role    INDEX     Z   CREATE INDEX idx_clscope_role ON public.client_scope_role_mapping USING btree (scope_id);
 $   DROP INDEX public.idx_clscope_role;
       public            hospital    false    276            7           1259    17623    idx_compo_config_compo    INDEX     [   CREATE INDEX idx_compo_config_compo ON public.component_config USING btree (component_id);
 *   DROP INDEX public.idx_compo_config_compo;
       public            hospital    false    293            :           1259    17891    idx_component_provider_type    INDEX     Z   CREATE INDEX idx_component_provider_type ON public.component USING btree (provider_type);
 /   DROP INDEX public.idx_component_provider_type;
       public            hospital    false    294            ;           1259    17622    idx_component_realm    INDEX     M   CREATE INDEX idx_component_realm ON public.component USING btree (realm_id);
 '   DROP INDEX public.idx_component_realm;
       public            hospital    false    294            F           1259    17624    idx_composite    INDEX     M   CREATE INDEX idx_composite ON public.composite_role USING btree (composite);
 !   DROP INDEX public.idx_composite;
       public            hospital    false    220            G           1259    17625    idx_composite_child    INDEX     T   CREATE INDEX idx_composite_child ON public.composite_role USING btree (child_role);
 '   DROP INDEX public.idx_composite_child;
       public            hospital    false    220            G           1259    17823    idx_defcls_realm    INDEX     U   CREATE INDEX idx_defcls_realm ON public.default_client_scope USING btree (realm_id);
 $   DROP INDEX public.idx_defcls_realm;
       public            hospital    false    299            H           1259    17824    idx_defcls_scope    INDEX     U   CREATE INDEX idx_defcls_scope ON public.default_client_scope USING btree (scope_id);
 $   DROP INDEX public.idx_defcls_scope;
       public            hospital    false    299            M           1259    17918    idx_event_time    INDEX     W   CREATE INDEX idx_event_time ON public.event_entity USING btree (realm_id, event_time);
 "   DROP INDEX public.idx_event_time;
       public            hospital    false    222    222            �           1259    17356    idx_fedidentity_feduser    INDEX     c   CREATE INDEX idx_fedidentity_feduser ON public.federated_identity USING btree (federated_user_id);
 +   DROP INDEX public.idx_fedidentity_feduser;
       public            hospital    false    245            �           1259    17355    idx_fedidentity_user    INDEX     V   CREATE INDEX idx_fedidentity_user ON public.federated_identity USING btree (user_id);
 (   DROP INDEX public.idx_fedidentity_user;
       public            hospital    false    245                       1259    17717    idx_fu_attribute    INDEX     b   CREATE INDEX idx_fu_attribute ON public.fed_user_attribute USING btree (user_id, realm_id, name);
 $   DROP INDEX public.idx_fu_attribute;
       public            hospital    false    287    287    287            "           1259    17737    idx_fu_cnsnt_ext    INDEX     }   CREATE INDEX idx_fu_cnsnt_ext ON public.fed_user_consent USING btree (user_id, client_storage_provider, external_client_id);
 $   DROP INDEX public.idx_fu_cnsnt_ext;
       public            hospital    false    288    288    288            #           1259    17900    idx_fu_consent    INDEX     Y   CREATE INDEX idx_fu_consent ON public.fed_user_consent USING btree (user_id, client_id);
 "   DROP INDEX public.idx_fu_consent;
       public            hospital    false    288    288            $           1259    17719    idx_fu_consent_ru    INDEX     [   CREATE INDEX idx_fu_consent_ru ON public.fed_user_consent USING btree (realm_id, user_id);
 %   DROP INDEX public.idx_fu_consent_ru;
       public            hospital    false    288    288            '           1259    17720    idx_fu_credential    INDEX     Z   CREATE INDEX idx_fu_credential ON public.fed_user_credential USING btree (user_id, type);
 %   DROP INDEX public.idx_fu_credential;
       public            hospital    false    289    289            (           1259    17721    idx_fu_credential_ru    INDEX     a   CREATE INDEX idx_fu_credential_ru ON public.fed_user_credential USING btree (realm_id, user_id);
 (   DROP INDEX public.idx_fu_credential_ru;
       public            hospital    false    289    289            +           1259    17722    idx_fu_group_membership    INDEX     j   CREATE INDEX idx_fu_group_membership ON public.fed_user_group_membership USING btree (user_id, group_id);
 +   DROP INDEX public.idx_fu_group_membership;
       public            hospital    false    290    290            ,           1259    17723    idx_fu_group_membership_ru    INDEX     m   CREATE INDEX idx_fu_group_membership_ru ON public.fed_user_group_membership USING btree (realm_id, user_id);
 .   DROP INDEX public.idx_fu_group_membership_ru;
       public            hospital    false    290    290            /           1259    17724    idx_fu_required_action    INDEX     o   CREATE INDEX idx_fu_required_action ON public.fed_user_required_action USING btree (user_id, required_action);
 *   DROP INDEX public.idx_fu_required_action;
       public            hospital    false    291    291            0           1259    17725    idx_fu_required_action_ru    INDEX     k   CREATE INDEX idx_fu_required_action_ru ON public.fed_user_required_action USING btree (realm_id, user_id);
 -   DROP INDEX public.idx_fu_required_action_ru;
       public            hospital    false    291    291            3           1259    17726    idx_fu_role_mapping    INDEX     a   CREATE INDEX idx_fu_role_mapping ON public.fed_user_role_mapping USING btree (user_id, role_id);
 '   DROP INDEX public.idx_fu_role_mapping;
       public            hospital    false    292    292            4           1259    17727    idx_fu_role_mapping_ru    INDEX     e   CREATE INDEX idx_fu_role_mapping_ru ON public.fed_user_role_mapping USING btree (realm_id, user_id);
 *   DROP INDEX public.idx_fu_role_mapping_ru;
       public            hospital    false    292    292            �           1259    17942    idx_group_att_by_name_value    INDEX     z   CREATE INDEX idx_group_att_by_name_value ON public.group_attribute USING btree (name, ((value)::character varying(250)));
 /   DROP INDEX public.idx_group_att_by_name_value;
       public            hospital    false    271    271            �           1259    17628    idx_group_attr_group    INDEX     T   CREATE INDEX idx_group_attr_group ON public.group_attribute USING btree (group_id);
 (   DROP INDEX public.idx_group_attr_group;
       public            hospital    false    271            �           1259    17629    idx_group_role_mapp_group    INDEX     \   CREATE INDEX idx_group_role_mapp_group ON public.group_role_mapping USING btree (group_id);
 -   DROP INDEX public.idx_group_role_mapp_group;
       public            hospital    false    270            �           1259    17631    idx_id_prov_mapp_realm    INDEX     _   CREATE INDEX idx_id_prov_mapp_realm ON public.identity_provider_mapper USING btree (realm_id);
 *   DROP INDEX public.idx_id_prov_mapp_realm;
       public            hospital    false    252            �           1259    17630    idx_ident_prov_realm    INDEX     V   CREATE INDEX idx_ident_prov_realm ON public.identity_provider USING btree (realm_id);
 (   DROP INDEX public.idx_ident_prov_realm;
       public            hospital    false    246            R           1259    17632    idx_keycloak_role_client    INDEX     T   CREATE INDEX idx_keycloak_role_client ON public.keycloak_role USING btree (client);
 ,   DROP INDEX public.idx_keycloak_role_client;
       public            hospital    false    223            S           1259    17633    idx_keycloak_role_realm    INDEX     R   CREATE INDEX idx_keycloak_role_realm ON public.keycloak_role USING btree (realm);
 +   DROP INDEX public.idx_keycloak_role_realm;
       public            hospital    false    223            �           1259    17935    idx_offline_css_preload    INDEX     m   CREATE INDEX idx_offline_css_preload ON public.offline_client_session USING btree (client_id, offline_flag);
 +   DROP INDEX public.idx_offline_css_preload;
       public            hospital    false    268    268            �           1259    17936    idx_offline_uss_by_user    INDEX     s   CREATE INDEX idx_offline_uss_by_user ON public.offline_user_session USING btree (user_id, realm_id, offline_flag);
 +   DROP INDEX public.idx_offline_uss_by_user;
       public            hospital    false    267    267    267            �           1259    17937    idx_offline_uss_by_usersess    INDEX        CREATE INDEX idx_offline_uss_by_usersess ON public.offline_user_session USING btree (realm_id, offline_flag, user_session_id);
 /   DROP INDEX public.idx_offline_uss_by_usersess;
       public            hospital    false    267    267    267            �           1259    17895    idx_offline_uss_createdon    INDEX     `   CREATE INDEX idx_offline_uss_createdon ON public.offline_user_session USING btree (created_on);
 -   DROP INDEX public.idx_offline_uss_createdon;
       public            hospital    false    267            �           1259    17926    idx_offline_uss_preload    INDEX     }   CREATE INDEX idx_offline_uss_preload ON public.offline_user_session USING btree (offline_flag, created_on, user_session_id);
 +   DROP INDEX public.idx_offline_uss_preload;
       public            hospital    false    267    267    267            �           1259    17634    idx_protocol_mapper_client    INDEX     [   CREATE INDEX idx_protocol_mapper_client ON public.protocol_mapper USING btree (client_id);
 .   DROP INDEX public.idx_protocol_mapper_client;
       public            hospital    false    243            [           1259    17637    idx_realm_attr_realm    INDEX     T   CREATE INDEX idx_realm_attr_realm ON public.realm_attribute USING btree (realm_id);
 (   DROP INDEX public.idx_realm_attr_realm;
       public            hospital    false    225            �           1259    17816    idx_realm_clscope    INDEX     N   CREATE INDEX idx_realm_clscope ON public.client_scope USING btree (realm_id);
 %   DROP INDEX public.idx_realm_clscope;
       public            hospital    false    274            �           1259    17638    idx_realm_def_grp_realm    INDEX     \   CREATE INDEX idx_realm_def_grp_realm ON public.realm_default_groups USING btree (realm_id);
 +   DROP INDEX public.idx_realm_def_grp_realm;
       public            hospital    false    273            ^           1259    17641    idx_realm_evt_list_realm    INDEX     _   CREATE INDEX idx_realm_evt_list_realm ON public.realm_events_listeners USING btree (realm_id);
 ,   DROP INDEX public.idx_realm_evt_list_realm;
       public            hospital    false    226            �           1259    17640    idx_realm_evt_types_realm    INDEX     c   CREATE INDEX idx_realm_evt_types_realm ON public.realm_enabled_event_types USING btree (realm_id);
 -   DROP INDEX public.idx_realm_evt_types_realm;
       public            hospital    false    250            V           1259    17636    idx_realm_master_adm_cli    INDEX     Y   CREATE INDEX idx_realm_master_adm_cli ON public.realm USING btree (master_admin_client);
 ,   DROP INDEX public.idx_realm_master_adm_cli;
       public            hospital    false    224            �           1259    17642    idx_realm_supp_local_realm    INDEX     b   CREATE INDEX idx_realm_supp_local_realm ON public.realm_supported_locales USING btree (realm_id);
 .   DROP INDEX public.idx_realm_supp_local_realm;
       public            hospital    false    248            e           1259    17643    idx_redir_uri_client    INDEX     S   CREATE INDEX idx_redir_uri_client ON public.redirect_uris USING btree (client_id);
 (   DROP INDEX public.idx_redir_uri_client;
       public            hospital    false    229            �           1259    17644    idx_req_act_prov_realm    INDEX     _   CREATE INDEX idx_req_act_prov_realm ON public.required_action_provider USING btree (realm_id);
 *   DROP INDEX public.idx_req_act_prov_realm;
       public            hospital    false    265                       1259    17645    idx_res_policy_policy    INDEX     V   CREATE INDEX idx_res_policy_policy ON public.resource_policy USING btree (policy_id);
 )   DROP INDEX public.idx_res_policy_policy;
       public            hospital    false    283                       1259    17646    idx_res_scope_scope    INDEX     R   CREATE INDEX idx_res_scope_scope ON public.resource_scope USING btree (scope_id);
 '   DROP INDEX public.idx_res_scope_scope;
       public            hospital    false    282            
           1259    17665    idx_res_serv_pol_res_serv    INDEX     j   CREATE INDEX idx_res_serv_pol_res_serv ON public.resource_server_policy USING btree (resource_server_id);
 -   DROP INDEX public.idx_res_serv_pol_res_serv;
       public            hospital    false    280                        1259    17666    idx_res_srv_res_res_srv    INDEX     j   CREATE INDEX idx_res_srv_res_res_srv ON public.resource_server_resource USING btree (resource_server_id);
 +   DROP INDEX public.idx_res_srv_res_res_srv;
       public            hospital    false    278                       1259    17667    idx_res_srv_scope_res_srv    INDEX     i   CREATE INDEX idx_res_srv_scope_res_srv ON public.resource_server_scope USING btree (resource_server_id);
 -   DROP INDEX public.idx_res_srv_scope_res_srv;
       public            hospital    false    279            Z           1259    17890    idx_role_attribute    INDEX     P   CREATE INDEX idx_role_attribute ON public.role_attribute USING btree (role_id);
 &   DROP INDEX public.idx_role_attribute;
       public            hospital    false    305            �           1259    17819    idx_role_clscope    INDEX     Y   CREATE INDEX idx_role_clscope ON public.client_scope_role_mapping USING btree (role_id);
 $   DROP INDEX public.idx_role_clscope;
       public            hospital    false    276            h           1259    17650    idx_scope_mapping_role    INDEX     S   CREATE INDEX idx_scope_mapping_role ON public.scope_mapping USING btree (role_id);
 *   DROP INDEX public.idx_scope_mapping_role;
       public            hospital    false    230                       1259    17651    idx_scope_policy_policy    INDEX     U   CREATE INDEX idx_scope_policy_policy ON public.scope_policy USING btree (policy_id);
 +   DROP INDEX public.idx_scope_policy_policy;
       public            hospital    false    284            �           1259    17898    idx_update_time    INDEX     R   CREATE INDEX idx_update_time ON public.migration_model USING btree (update_time);
 #   DROP INDEX public.idx_update_time;
       public            hospital    false    251            �           1259    17345    idx_us_sess_id_on_cl_sess    INDEX     g   CREATE INDEX idx_us_sess_id_on_cl_sess ON public.offline_client_session USING btree (user_session_id);
 -   DROP INDEX public.idx_us_sess_id_on_cl_sess;
       public            hospital    false    268            M           1259    17825    idx_usconsent_clscope    INDEX     f   CREATE INDEX idx_usconsent_clscope ON public.user_consent_client_scope USING btree (user_consent_id);
 )   DROP INDEX public.idx_usconsent_clscope;
       public            hospital    false    300            m           1259    17352    idx_user_attribute    INDEX     P   CREATE INDEX idx_user_attribute ON public.user_attribute USING btree (user_id);
 &   DROP INDEX public.idx_user_attribute;
       public            hospital    false    232            n           1259    17939    idx_user_attribute_name    INDEX     Y   CREATE INDEX idx_user_attribute_name ON public.user_attribute USING btree (name, value);
 +   DROP INDEX public.idx_user_attribute_name;
       public            hospital    false    232    232            �           1259    17349    idx_user_consent    INDEX     L   CREATE INDEX idx_user_consent ON public.user_consent USING btree (user_id);
 $   DROP INDEX public.idx_user_consent;
       public            hospital    false    254            J           1259    17353    idx_user_credential    INDEX     M   CREATE INDEX idx_user_credential ON public.credential USING btree (user_id);
 '   DROP INDEX public.idx_user_credential;
       public            hospital    false    221            q           1259    17346    idx_user_email    INDEX     G   CREATE INDEX idx_user_email ON public.user_entity USING btree (email);
 "   DROP INDEX public.idx_user_email;
       public            hospital    false    233            �           1259    17348    idx_user_group_mapping    INDEX     [   CREATE INDEX idx_user_group_mapping ON public.user_group_membership USING btree (user_id);
 *   DROP INDEX public.idx_user_group_mapping;
       public            hospital    false    272            ~           1259    17354    idx_user_reqactions    INDEX     W   CREATE INDEX idx_user_reqactions ON public.user_required_action USING btree (user_id);
 '   DROP INDEX public.idx_user_reqactions;
       public            hospital    false    236            �           1259    17347    idx_user_role_mapping    INDEX     V   CREATE INDEX idx_user_role_mapping ON public.user_role_mapping USING btree (user_id);
 )   DROP INDEX public.idx_user_role_mapping;
       public            hospital    false    237            r           1259    17940    idx_user_service_account    INDEX     q   CREATE INDEX idx_user_service_account ON public.user_entity USING btree (realm_id, service_account_client_link);
 ,   DROP INDEX public.idx_user_service_account;
       public            hospital    false    233    233            �           1259    17653    idx_usr_fed_map_fed_prv    INDEX     l   CREATE INDEX idx_usr_fed_map_fed_prv ON public.user_federation_mapper USING btree (federation_provider_id);
 +   DROP INDEX public.idx_usr_fed_map_fed_prv;
       public            hospital    false    261            �           1259    17654    idx_usr_fed_map_realm    INDEX     \   CREATE INDEX idx_usr_fed_map_realm ON public.user_federation_mapper USING btree (realm_id);
 )   DROP INDEX public.idx_usr_fed_map_realm;
       public            hospital    false    261            {           1259    17655    idx_usr_fed_prv_realm    INDEX     ^   CREATE INDEX idx_usr_fed_prv_realm ON public.user_federation_provider USING btree (realm_id);
 )   DROP INDEX public.idx_usr_fed_prv_realm;
       public            hospital    false    235            �           1259    17656    idx_web_orig_client    INDEX     P   CREATE INDEX idx_web_orig_client ON public.web_origins USING btree (client_id);
 '   DROP INDEX public.idx_web_orig_client;
       public            hospital    false    239            �           2606    17081 1   client_session_auth_status auth_status_constraint    FK CONSTRAINT     �   ALTER TABLE ONLY public.client_session_auth_status
    ADD CONSTRAINT auth_status_constraint FOREIGN KEY (client_session) REFERENCES public.client_session(id);
 [   ALTER TABLE ONLY public.client_session_auth_status DROP CONSTRAINT auth_status_constraint;
       public          hospital    false    218    263    3648            v           2606    16850 $   identity_provider fk2b4ebc52ae5c3b34    FK CONSTRAINT     �   ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT fk2b4ebc52ae5c3b34 FOREIGN KEY (realm_id) REFERENCES public.realm(id);
 N   ALTER TABLE ONLY public.identity_provider DROP CONSTRAINT fk2b4ebc52ae5c3b34;
       public          hospital    false    3669    246    224            o           2606    16780 $   client_attributes fk3c47c64beacca966    FK CONSTRAINT     �   ALTER TABLE ONLY public.client_attributes
    ADD CONSTRAINT fk3c47c64beacca966 FOREIGN KEY (client_id) REFERENCES public.client(id);
 N   ALTER TABLE ONLY public.client_attributes DROP CONSTRAINT fk3c47c64beacca966;
       public          hospital    false    217    240    3643            u           2606    16860 %   federated_identity fk404288b92ef007a6    FK CONSTRAINT     �   ALTER TABLE ONLY public.federated_identity
    ADD CONSTRAINT fk404288b92ef007a6 FOREIGN KEY (user_id) REFERENCES public.user_entity(id);
 O   ALTER TABLE ONLY public.federated_identity DROP CONSTRAINT fk404288b92ef007a6;
       public          hospital    false    233    3696    245            q           2606    17007 ,   client_node_registrations fk4129723ba992f594    FK CONSTRAINT     �   ALTER TABLE ONLY public.client_node_registrations
    ADD CONSTRAINT fk4129723ba992f594 FOREIGN KEY (client_id) REFERENCES public.client(id);
 V   ALTER TABLE ONLY public.client_node_registrations DROP CONSTRAINT fk4129723ba992f594;
       public          hospital    false    242    3643    217            p           2606    16785 &   client_session_note fk5edfb00ff51c2736    FK CONSTRAINT     �   ALTER TABLE ONLY public.client_session_note
    ADD CONSTRAINT fk5edfb00ff51c2736 FOREIGN KEY (client_session) REFERENCES public.client_session(id);
 P   ALTER TABLE ONLY public.client_session_note DROP CONSTRAINT fk5edfb00ff51c2736;
       public          hospital    false    218    241    3648            y           2606    16890 $   user_session_note fk5edfb00ff51d3472    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_session_note
    ADD CONSTRAINT fk5edfb00ff51d3472 FOREIGN KEY (user_session) REFERENCES public.user_session(id);
 N   ALTER TABLE ONLY public.user_session_note DROP CONSTRAINT fk5edfb00ff51d3472;
       public          hospital    false    238    249    3715            ^           2606    16600 0   client_session_role fk_11b7sgqw18i532811v7o2dv76    FK CONSTRAINT     �   ALTER TABLE ONLY public.client_session_role
    ADD CONSTRAINT fk_11b7sgqw18i532811v7o2dv76 FOREIGN KEY (client_session) REFERENCES public.client_session(id);
 Z   ALTER TABLE ONLY public.client_session_role DROP CONSTRAINT fk_11b7sgqw18i532811v7o2dv76;
       public          hospital    false    218    219    3648            g           2606    16605 *   redirect_uris fk_1burs8pb4ouj97h5wuppahv9f    FK CONSTRAINT     �   ALTER TABLE ONLY public.redirect_uris
    ADD CONSTRAINT fk_1burs8pb4ouj97h5wuppahv9f FOREIGN KEY (client_id) REFERENCES public.client(id);
 T   ALTER TABLE ONLY public.redirect_uris DROP CONSTRAINT fk_1burs8pb4ouj97h5wuppahv9f;
       public          hospital    false    217    3643    229            k           2606    16610 5   user_federation_provider fk_1fj32f6ptolw2qy60cd8n01e8    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_federation_provider
    ADD CONSTRAINT fk_1fj32f6ptolw2qy60cd8n01e8 FOREIGN KEY (realm_id) REFERENCES public.realm(id);
 _   ALTER TABLE ONLY public.user_federation_provider DROP CONSTRAINT fk_1fj32f6ptolw2qy60cd8n01e8;
       public          hospital    false    3669    224    235            ~           2606    16985 7   client_session_prot_mapper fk_33a8sgqw18i532811v7o2dk89    FK CONSTRAINT     �   ALTER TABLE ONLY public.client_session_prot_mapper
    ADD CONSTRAINT fk_33a8sgqw18i532811v7o2dk89 FOREIGN KEY (client_session) REFERENCES public.client_session(id);
 a   ALTER TABLE ONLY public.client_session_prot_mapper DROP CONSTRAINT fk_33a8sgqw18i532811v7o2dk89;
       public          hospital    false    255    218    3648            e           2606    16620 6   realm_required_credential fk_5hg65lybevavkqfki3kponh9v    FK CONSTRAINT     �   ALTER TABLE ONLY public.realm_required_credential
    ADD CONSTRAINT fk_5hg65lybevavkqfki3kponh9v FOREIGN KEY (realm_id) REFERENCES public.realm(id);
 `   ALTER TABLE ONLY public.realm_required_credential DROP CONSTRAINT fk_5hg65lybevavkqfki3kponh9v;
       public          hospital    false    224    3669    227            �           2606    17858 /   resource_attribute fk_5hrm2vlf9ql5fu022kqepovbr    FK CONSTRAINT     �   ALTER TABLE ONLY public.resource_attribute
    ADD CONSTRAINT fk_5hrm2vlf9ql5fu022kqepovbr FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);
 Y   ALTER TABLE ONLY public.resource_attribute DROP CONSTRAINT fk_5hrm2vlf9ql5fu022kqepovbr;
       public          hospital    false    3839    278    303            i           2606    16625 +   user_attribute fk_5hrm2vlf9ql5fu043kqepovbr    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_attribute
    ADD CONSTRAINT fk_5hrm2vlf9ql5fu043kqepovbr FOREIGN KEY (user_id) REFERENCES public.user_entity(id);
 U   ALTER TABLE ONLY public.user_attribute DROP CONSTRAINT fk_5hrm2vlf9ql5fu043kqepovbr;
       public          hospital    false    233    3696    232            l           2606    16635 1   user_required_action fk_6qj3w1jw9cvafhe19bwsiuvmd    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_required_action
    ADD CONSTRAINT fk_6qj3w1jw9cvafhe19bwsiuvmd FOREIGN KEY (user_id) REFERENCES public.user_entity(id);
 [   ALTER TABLE ONLY public.user_required_action DROP CONSTRAINT fk_6qj3w1jw9cvafhe19bwsiuvmd;
       public          hospital    false    3696    233    236            b           2606    16640 *   keycloak_role fk_6vyqfe4cn4wlq8r6kt5vdsj5c    FK CONSTRAINT     �   ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT fk_6vyqfe4cn4wlq8r6kt5vdsj5c FOREIGN KEY (realm) REFERENCES public.realm(id);
 T   ALTER TABLE ONLY public.keycloak_role DROP CONSTRAINT fk_6vyqfe4cn4wlq8r6kt5vdsj5c;
       public          hospital    false    223    224    3669            f           2606    16645 .   realm_smtp_config fk_70ej8xdxgxd0b9hh6180irr0o    FK CONSTRAINT     �   ALTER TABLE ONLY public.realm_smtp_config
    ADD CONSTRAINT fk_70ej8xdxgxd0b9hh6180irr0o FOREIGN KEY (realm_id) REFERENCES public.realm(id);
 X   ALTER TABLE ONLY public.realm_smtp_config DROP CONSTRAINT fk_70ej8xdxgxd0b9hh6180irr0o;
       public          hospital    false    3669    224    228            c           2606    16660 ,   realm_attribute fk_8shxd6l3e9atqukacxgpffptw    FK CONSTRAINT     �   ALTER TABLE ONLY public.realm_attribute
    ADD CONSTRAINT fk_8shxd6l3e9atqukacxgpffptw FOREIGN KEY (realm_id) REFERENCES public.realm(id);
 V   ALTER TABLE ONLY public.realm_attribute DROP CONSTRAINT fk_8shxd6l3e9atqukacxgpffptw;
       public          hospital    false    225    224    3669            _           2606    16665 +   composite_role fk_a63wvekftu8jo1pnj81e7mce2    FK CONSTRAINT     �   ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT fk_a63wvekftu8jo1pnj81e7mce2 FOREIGN KEY (composite) REFERENCES public.keycloak_role(id);
 U   ALTER TABLE ONLY public.composite_role DROP CONSTRAINT fk_a63wvekftu8jo1pnj81e7mce2;
       public          hospital    false    220    3665    223            �           2606    17101 *   authentication_execution fk_auth_exec_flow    FK CONSTRAINT     �   ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT fk_auth_exec_flow FOREIGN KEY (flow_id) REFERENCES public.authentication_flow(id);
 T   ALTER TABLE ONLY public.authentication_execution DROP CONSTRAINT fk_auth_exec_flow;
       public          hospital    false    258    3772    259            �           2606    17096 +   authentication_execution fk_auth_exec_realm    FK CONSTRAINT     �   ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT fk_auth_exec_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);
 U   ALTER TABLE ONLY public.authentication_execution DROP CONSTRAINT fk_auth_exec_realm;
       public          hospital    false    3669    259    224            �           2606    17091 &   authentication_flow fk_auth_flow_realm    FK CONSTRAINT     �   ALTER TABLE ONLY public.authentication_flow
    ADD CONSTRAINT fk_auth_flow_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);
 P   ALTER TABLE ONLY public.authentication_flow DROP CONSTRAINT fk_auth_flow_realm;
       public          hospital    false    3669    258    224                       2606    17086 "   authenticator_config fk_auth_realm    FK CONSTRAINT     �   ALTER TABLE ONLY public.authenticator_config
    ADD CONSTRAINT fk_auth_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);
 L   ALTER TABLE ONLY public.authenticator_config DROP CONSTRAINT fk_auth_realm;
       public          hospital    false    224    3669    257            ]           2606    16670 +   client_session fk_b4ao2vcvat6ukau74wbwtfqo1    FK CONSTRAINT     �   ALTER TABLE ONLY public.client_session
    ADD CONSTRAINT fk_b4ao2vcvat6ukau74wbwtfqo1 FOREIGN KEY (session_id) REFERENCES public.user_session(id);
 U   ALTER TABLE ONLY public.client_session DROP CONSTRAINT fk_b4ao2vcvat6ukau74wbwtfqo1;
       public          hospital    false    218    238    3715            m           2606    16675 .   user_role_mapping fk_c4fqv34p1mbylloxang7b1q3l    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_role_mapping
    ADD CONSTRAINT fk_c4fqv34p1mbylloxang7b1q3l FOREIGN KEY (user_id) REFERENCES public.user_entity(id);
 X   ALTER TABLE ONLY public.user_role_mapping DROP CONSTRAINT fk_c4fqv34p1mbylloxang7b1q3l;
       public          hospital    false    233    3696    237            �           2606    17764 .   client_scope_attributes fk_cl_scope_attr_scope    FK CONSTRAINT     �   ALTER TABLE ONLY public.client_scope_attributes
    ADD CONSTRAINT fk_cl_scope_attr_scope FOREIGN KEY (scope_id) REFERENCES public.client_scope(id);
 X   ALTER TABLE ONLY public.client_scope_attributes DROP CONSTRAINT fk_cl_scope_attr_scope;
       public          hospital    false    3826    274    275            �           2606    17754 .   client_scope_role_mapping fk_cl_scope_rm_scope    FK CONSTRAINT     �   ALTER TABLE ONLY public.client_scope_role_mapping
    ADD CONSTRAINT fk_cl_scope_rm_scope FOREIGN KEY (scope_id) REFERENCES public.client_scope(id);
 X   ALTER TABLE ONLY public.client_scope_role_mapping DROP CONSTRAINT fk_cl_scope_rm_scope;
       public          hospital    false    276    3826    274            �           2606    17170 +   client_user_session_note fk_cl_usr_ses_note    FK CONSTRAINT     �   ALTER TABLE ONLY public.client_user_session_note
    ADD CONSTRAINT fk_cl_usr_ses_note FOREIGN KEY (client_session) REFERENCES public.client_session(id);
 U   ALTER TABLE ONLY public.client_user_session_note DROP CONSTRAINT fk_cl_usr_ses_note;
       public          hospital    false    264    218    3648            r           2606    17749 #   protocol_mapper fk_cli_scope_mapper    FK CONSTRAINT     �   ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT fk_cli_scope_mapper FOREIGN KEY (client_scope_id) REFERENCES public.client_scope(id);
 M   ALTER TABLE ONLY public.protocol_mapper DROP CONSTRAINT fk_cli_scope_mapper;
       public          hospital    false    3826    243    274            �           2606    17608 .   client_initial_access fk_client_init_acc_realm    FK CONSTRAINT     �   ALTER TABLE ONLY public.client_initial_access
    ADD CONSTRAINT fk_client_init_acc_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);
 X   ALTER TABLE ONLY public.client_initial_access DROP CONSTRAINT fk_client_init_acc_realm;
       public          hospital    false    3669    296    224            �           2606    17556 $   component_config fk_component_config    FK CONSTRAINT     �   ALTER TABLE ONLY public.component_config
    ADD CONSTRAINT fk_component_config FOREIGN KEY (component_id) REFERENCES public.component(id);
 N   ALTER TABLE ONLY public.component_config DROP CONSTRAINT fk_component_config;
       public          hospital    false    3897    294    293            �           2606    17551    component fk_component_realm    FK CONSTRAINT     |   ALTER TABLE ONLY public.component
    ADD CONSTRAINT fk_component_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);
 F   ALTER TABLE ONLY public.component DROP CONSTRAINT fk_component_realm;
       public          hospital    false    3669    224    294            �           2606    17256 (   realm_default_groups fk_def_groups_realm    FK CONSTRAINT     �   ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT fk_def_groups_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);
 R   ALTER TABLE ONLY public.realm_default_groups DROP CONSTRAINT fk_def_groups_realm;
       public          hospital    false    224    273    3669            �           2606    17116 .   user_federation_mapper_config fk_fedmapper_cfg    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_federation_mapper_config
    ADD CONSTRAINT fk_fedmapper_cfg FOREIGN KEY (user_federation_mapper_id) REFERENCES public.user_federation_mapper(id);
 X   ALTER TABLE ONLY public.user_federation_mapper_config DROP CONSTRAINT fk_fedmapper_cfg;
       public          hospital    false    261    262    3781            �           2606    17111 ,   user_federation_mapper fk_fedmapperpm_fedprv    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT fk_fedmapperpm_fedprv FOREIGN KEY (federation_provider_id) REFERENCES public.user_federation_provider(id);
 V   ALTER TABLE ONLY public.user_federation_mapper DROP CONSTRAINT fk_fedmapperpm_fedprv;
       public          hospital    false    3706    261    235            �           2606    17106 +   user_federation_mapper fk_fedmapperpm_realm    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT fk_fedmapperpm_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);
 U   ALTER TABLE ONLY public.user_federation_mapper DROP CONSTRAINT fk_fedmapperpm_realm;
       public          hospital    false    261    3669    224            �           2606    17474 .   associated_policy fk_frsr5s213xcx4wnkog82ssrfy    FK CONSTRAINT     �   ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT fk_frsr5s213xcx4wnkog82ssrfy FOREIGN KEY (associated_policy_id) REFERENCES public.resource_server_policy(id);
 X   ALTER TABLE ONLY public.associated_policy DROP CONSTRAINT fk_frsr5s213xcx4wnkog82ssrfy;
       public          hospital    false    280    285    3849            �           2606    17459 )   scope_policy fk_frsrasp13xcx4wnkog82ssrfy    FK CONSTRAINT     �   ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT fk_frsrasp13xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);
 S   ALTER TABLE ONLY public.scope_policy DROP CONSTRAINT fk_frsrasp13xcx4wnkog82ssrfy;
       public          hospital    false    284    3849    280            �           2606    17831 8   resource_server_perm_ticket fk_frsrho213xcx4wnkog82sspmt    FK CONSTRAINT     �   ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog82sspmt FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);
 b   ALTER TABLE ONLY public.resource_server_perm_ticket DROP CONSTRAINT fk_frsrho213xcx4wnkog82sspmt;
       public          hospital    false    277    302    3837            �           2606    17675 5   resource_server_resource fk_frsrho213xcx4wnkog82ssrfy    FK CONSTRAINT     �   ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT fk_frsrho213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);
 _   ALTER TABLE ONLY public.resource_server_resource DROP CONSTRAINT fk_frsrho213xcx4wnkog82ssrfy;
       public          hospital    false    278    3837    277            �           2606    17836 8   resource_server_perm_ticket fk_frsrho213xcx4wnkog83sspmt    FK CONSTRAINT     �   ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog83sspmt FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);
 b   ALTER TABLE ONLY public.resource_server_perm_ticket DROP CONSTRAINT fk_frsrho213xcx4wnkog83sspmt;
       public          hospital    false    302    3839    278            �           2606    17841 8   resource_server_perm_ticket fk_frsrho213xcx4wnkog84sspmt    FK CONSTRAINT     �   ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog84sspmt FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);
 b   ALTER TABLE ONLY public.resource_server_perm_ticket DROP CONSTRAINT fk_frsrho213xcx4wnkog84sspmt;
       public          hospital    false    302    279    3844            �           2606    17469 .   associated_policy fk_frsrpas14xcx4wnkog82ssrfy    FK CONSTRAINT     �   ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT fk_frsrpas14xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);
 X   ALTER TABLE ONLY public.associated_policy DROP CONSTRAINT fk_frsrpas14xcx4wnkog82ssrfy;
       public          hospital    false    3849    285    280            �           2606    17454 )   scope_policy fk_frsrpass3xcx4wnkog82ssrfy    FK CONSTRAINT     �   ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT fk_frsrpass3xcx4wnkog82ssrfy FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);
 S   ALTER TABLE ONLY public.scope_policy DROP CONSTRAINT fk_frsrpass3xcx4wnkog82ssrfy;
       public          hospital    false    3844    279    284            �           2606    17863 8   resource_server_perm_ticket fk_frsrpo2128cx4wnkog82ssrfy    FK CONSTRAINT     �   ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrpo2128cx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);
 b   ALTER TABLE ONLY public.resource_server_perm_ticket DROP CONSTRAINT fk_frsrpo2128cx4wnkog82ssrfy;
       public          hospital    false    280    302    3849            �           2606    17670 3   resource_server_policy fk_frsrpo213xcx4wnkog82ssrfy    FK CONSTRAINT     �   ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT fk_frsrpo213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);
 ]   ALTER TABLE ONLY public.resource_server_policy DROP CONSTRAINT fk_frsrpo213xcx4wnkog82ssrfy;
       public          hospital    false    280    3837    277            �           2606    17424 +   resource_scope fk_frsrpos13xcx4wnkog82ssrfy    FK CONSTRAINT     �   ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT fk_frsrpos13xcx4wnkog82ssrfy FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);
 U   ALTER TABLE ONLY public.resource_scope DROP CONSTRAINT fk_frsrpos13xcx4wnkog82ssrfy;
       public          hospital    false    278    282    3839            �           2606    17439 ,   resource_policy fk_frsrpos53xcx4wnkog82ssrfy    FK CONSTRAINT     �   ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT fk_frsrpos53xcx4wnkog82ssrfy FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);
 V   ALTER TABLE ONLY public.resource_policy DROP CONSTRAINT fk_frsrpos53xcx4wnkog82ssrfy;
       public          hospital    false    283    3839    278            �           2606    17444 ,   resource_policy fk_frsrpp213xcx4wnkog82ssrfy    FK CONSTRAINT     �   ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT fk_frsrpp213xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);
 V   ALTER TABLE ONLY public.resource_policy DROP CONSTRAINT fk_frsrpp213xcx4wnkog82ssrfy;
       public          hospital    false    3849    280    283            �           2606    17429 +   resource_scope fk_frsrps213xcx4wnkog82ssrfy    FK CONSTRAINT     �   ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT fk_frsrps213xcx4wnkog82ssrfy FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);
 U   ALTER TABLE ONLY public.resource_scope DROP CONSTRAINT fk_frsrps213xcx4wnkog82ssrfy;
       public          hospital    false    279    282    3844            �           2606    17680 2   resource_server_scope fk_frsrso213xcx4wnkog82ssrfy    FK CONSTRAINT     �   ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT fk_frsrso213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);
 \   ALTER TABLE ONLY public.resource_server_scope DROP CONSTRAINT fk_frsrso213xcx4wnkog82ssrfy;
       public          hospital    false    279    277    3837            `           2606    16690 +   composite_role fk_gr7thllb9lu8q4vqa4524jjy8    FK CONSTRAINT     �   ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT fk_gr7thllb9lu8q4vqa4524jjy8 FOREIGN KEY (child_role) REFERENCES public.keycloak_role(id);
 U   ALTER TABLE ONLY public.composite_role DROP CONSTRAINT fk_gr7thllb9lu8q4vqa4524jjy8;
       public          hospital    false    223    3665    220            �           2606    17806 .   user_consent_client_scope fk_grntcsnt_clsc_usc    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_consent_client_scope
    ADD CONSTRAINT fk_grntcsnt_clsc_usc FOREIGN KEY (user_consent_id) REFERENCES public.user_consent(id);
 X   ALTER TABLE ONLY public.user_consent_client_scope DROP CONSTRAINT fk_grntcsnt_clsc_usc;
       public          hospital    false    3759    300    254            }           2606    16970    user_consent fk_grntcsnt_user    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT fk_grntcsnt_user FOREIGN KEY (user_id) REFERENCES public.user_entity(id);
 G   ALTER TABLE ONLY public.user_consent DROP CONSTRAINT fk_grntcsnt_user;
       public          hospital    false    254    3696    233            �           2606    17230 (   group_attribute fk_group_attribute_group    FK CONSTRAINT     �   ALTER TABLE ONLY public.group_attribute
    ADD CONSTRAINT fk_group_attribute_group FOREIGN KEY (group_id) REFERENCES public.keycloak_group(id);
 R   ALTER TABLE ONLY public.group_attribute DROP CONSTRAINT fk_group_attribute_group;
       public          hospital    false    271    3806    269            �           2606    17244 &   group_role_mapping fk_group_role_group    FK CONSTRAINT     �   ALTER TABLE ONLY public.group_role_mapping
    ADD CONSTRAINT fk_group_role_group FOREIGN KEY (group_id) REFERENCES public.keycloak_group(id);
 P   ALTER TABLE ONLY public.group_role_mapping DROP CONSTRAINT fk_group_role_group;
       public          hospital    false    269    270    3806            z           2606    16916 6   realm_enabled_event_types fk_h846o4h0w8epx5nwedrf5y69j    FK CONSTRAINT     �   ALTER TABLE ONLY public.realm_enabled_event_types
    ADD CONSTRAINT fk_h846o4h0w8epx5nwedrf5y69j FOREIGN KEY (realm_id) REFERENCES public.realm(id);
 `   ALTER TABLE ONLY public.realm_enabled_event_types DROP CONSTRAINT fk_h846o4h0w8epx5nwedrf5y69j;
       public          hospital    false    3669    250    224            d           2606    16700 3   realm_events_listeners fk_h846o4h0w8epx5nxev9f5y69j    FK CONSTRAINT     �   ALTER TABLE ONLY public.realm_events_listeners
    ADD CONSTRAINT fk_h846o4h0w8epx5nxev9f5y69j FOREIGN KEY (realm_id) REFERENCES public.realm(id);
 ]   ALTER TABLE ONLY public.realm_events_listeners DROP CONSTRAINT fk_h846o4h0w8epx5nxev9f5y69j;
       public          hospital    false    3669    224    226            {           2606    16960 &   identity_provider_mapper fk_idpm_realm    FK CONSTRAINT     �   ALTER TABLE ONLY public.identity_provider_mapper
    ADD CONSTRAINT fk_idpm_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);
 P   ALTER TABLE ONLY public.identity_provider_mapper DROP CONSTRAINT fk_idpm_realm;
       public          hospital    false    224    3669    252            |           2606    17130    idp_mapper_config fk_idpmconfig    FK CONSTRAINT     �   ALTER TABLE ONLY public.idp_mapper_config
    ADD CONSTRAINT fk_idpmconfig FOREIGN KEY (idp_mapper_id) REFERENCES public.identity_provider_mapper(id);
 I   ALTER TABLE ONLY public.idp_mapper_config DROP CONSTRAINT fk_idpmconfig;
       public          hospital    false    252    3754    253            n           2606    16710 (   web_origins fk_lojpho213xcx4wnkog82ssrfy    FK CONSTRAINT     �   ALTER TABLE ONLY public.web_origins
    ADD CONSTRAINT fk_lojpho213xcx4wnkog82ssrfy FOREIGN KEY (client_id) REFERENCES public.client(id);
 R   ALTER TABLE ONLY public.web_origins DROP CONSTRAINT fk_lojpho213xcx4wnkog82ssrfy;
       public          hospital    false    239    217    3643            h           2606    16720 *   scope_mapping fk_ouse064plmlr732lxjcn1q5f1    FK CONSTRAINT     �   ALTER TABLE ONLY public.scope_mapping
    ADD CONSTRAINT fk_ouse064plmlr732lxjcn1q5f1 FOREIGN KEY (client_id) REFERENCES public.client(id);
 T   ALTER TABLE ONLY public.scope_mapping DROP CONSTRAINT fk_ouse064plmlr732lxjcn1q5f1;
       public          hospital    false    230    3643    217            s           2606    16855    protocol_mapper fk_pcm_realm    FK CONSTRAINT     ~   ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT fk_pcm_realm FOREIGN KEY (client_id) REFERENCES public.client(id);
 F   ALTER TABLE ONLY public.protocol_mapper DROP CONSTRAINT fk_pcm_realm;
       public          hospital    false    217    243    3643            a           2606    16735 '   credential fk_pfyr0glasqyl0dei3kl69r6v0    FK CONSTRAINT     �   ALTER TABLE ONLY public.credential
    ADD CONSTRAINT fk_pfyr0glasqyl0dei3kl69r6v0 FOREIGN KEY (user_id) REFERENCES public.user_entity(id);
 Q   ALTER TABLE ONLY public.credential DROP CONSTRAINT fk_pfyr0glasqyl0dei3kl69r6v0;
       public          hospital    false    233    3696    221            t           2606    17123 "   protocol_mapper_config fk_pmconfig    FK CONSTRAINT     �   ALTER TABLE ONLY public.protocol_mapper_config
    ADD CONSTRAINT fk_pmconfig FOREIGN KEY (protocol_mapper_id) REFERENCES public.protocol_mapper(id);
 L   ALTER TABLE ONLY public.protocol_mapper_config DROP CONSTRAINT fk_pmconfig;
       public          hospital    false    3726    243    244            �           2606    17791 -   default_client_scope fk_r_def_cli_scope_realm    FK CONSTRAINT     �   ALTER TABLE ONLY public.default_client_scope
    ADD CONSTRAINT fk_r_def_cli_scope_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);
 W   ALTER TABLE ONLY public.default_client_scope DROP CONSTRAINT fk_r_def_cli_scope_realm;
       public          hospital    false    224    3669    299            �           2606    17165 )   required_action_provider fk_req_act_realm    FK CONSTRAINT     �   ALTER TABLE ONLY public.required_action_provider
    ADD CONSTRAINT fk_req_act_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);
 S   ALTER TABLE ONLY public.required_action_provider DROP CONSTRAINT fk_req_act_realm;
       public          hospital    false    224    3669    265            �           2606    17871 %   resource_uris fk_resource_server_uris    FK CONSTRAINT     �   ALTER TABLE ONLY public.resource_uris
    ADD CONSTRAINT fk_resource_server_uris FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);
 O   ALTER TABLE ONLY public.resource_uris DROP CONSTRAINT fk_resource_server_uris;
       public          hospital    false    304    278    3839            �           2606    17885 #   role_attribute fk_role_attribute_id    FK CONSTRAINT     �   ALTER TABLE ONLY public.role_attribute
    ADD CONSTRAINT fk_role_attribute_id FOREIGN KEY (role_id) REFERENCES public.keycloak_role(id);
 M   ALTER TABLE ONLY public.role_attribute DROP CONSTRAINT fk_role_attribute_id;
       public          hospital    false    305    223    3665            x           2606    16885 2   realm_supported_locales fk_supported_locales_realm    FK CONSTRAINT     �   ALTER TABLE ONLY public.realm_supported_locales
    ADD CONSTRAINT fk_supported_locales_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);
 \   ALTER TABLE ONLY public.realm_supported_locales DROP CONSTRAINT fk_supported_locales_realm;
       public          hospital    false    248    3669    224            j           2606    16755 3   user_federation_config fk_t13hpu1j94r2ebpekr39x5eu5    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_federation_config
    ADD CONSTRAINT fk_t13hpu1j94r2ebpekr39x5eu5 FOREIGN KEY (user_federation_provider_id) REFERENCES public.user_federation_provider(id);
 ]   ALTER TABLE ONLY public.user_federation_config DROP CONSTRAINT fk_t13hpu1j94r2ebpekr39x5eu5;
       public          hospital    false    235    3706    234            �           2606    17237 (   user_group_membership fk_user_group_user    FK CONSTRAINT     �   ALTER TABLE ONLY public.user_group_membership
    ADD CONSTRAINT fk_user_group_user FOREIGN KEY (user_id) REFERENCES public.user_entity(id);
 R   ALTER TABLE ONLY public.user_group_membership DROP CONSTRAINT fk_user_group_user;
       public          hospital    false    272    233    3696            �           2606    17414 !   policy_config fkdc34197cf864c4e43    FK CONSTRAINT     �   ALTER TABLE ONLY public.policy_config
    ADD CONSTRAINT fkdc34197cf864c4e43 FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);
 K   ALTER TABLE ONLY public.policy_config DROP CONSTRAINT fkdc34197cf864c4e43;
       public          hospital    false    3849    280    281            w           2606    16865 +   identity_provider_config fkdc4897cf864c4e43    FK CONSTRAINT     �   ALTER TABLE ONLY public.identity_provider_config
    ADD CONSTRAINT fkdc4897cf864c4e43 FOREIGN KEY (identity_provider_id) REFERENCES public.identity_provider(internal_id);
 U   ALTER TABLE ONLY public.identity_provider_config DROP CONSTRAINT fkdc4897cf864c4e43;
       public          hospital    false    246    247    3736            _      x������ � �      |      x������ � �      b      x��[mrǎ�Mޥ6�
�x'�Tes-�z$e��~M��!�p��l�CJ��9( �	4�_�K�.U_]�]�g�kt����/���oϿ������޴�C�5�wyu�2��F3��nVU�^�Ւ��3��9'�'�#P��&������붬�W�������QK?z<��D�oP����W��n�;�U���ctyLv\fw��ĢL��}�N�������Ǉ?�ã{T�{����x�0��͓hJs8��\.�ܠ]i!��q����WE�����ږgu��88�5����B�0j�9�f�5`\xp���,}��$�{V}{��{�C�W~z���Q�zx�c� �eyȢ��-O|݌|��`|Sܻ��/
��`��|3r��Dn��f��K�n�\ki)�cd�KuA<>�kqc�F=��r��p>������=9���_w�~C��t����K%w\��uY��A��P$�;ه���&��2%i3�݌�'d��䝏�߽x��$ݯ�|����/w����w{��RF[.+2�BS��(=��H5��d�|��+����GM��5���b�*�����K�;hq���X�q��e�9��K�`Axi#uQ$���s����:/��qp}�$�m�9�i�	Fq��juR�Jl:;�V� ;C@���х��S-�YT���qU`'��ksA�*�!��P���&\h��Q������#[ؐl����S��Mq��]�M����L�Ƙ8������=��_^��SB`$IY�S/R7׶��yt5���rMEF�X?�~9�G=~�þ��,�އY��S���8@��)a�2?G��a�}Q�����d҉S��J[����	?Rk���+��?`+��f���\��|R����D�bɟ��X��������x��%����3d/t���L����I�-���_�������<6��	�D�A �:l�Ƀz nI罅�oiIwh$�J�Sk*�DP����?���UQ��A1 uA�P�e��9��eD����;���kM:9@ܩ���+C�3�(����~�0�%��� X�=�����@K� uBQH�rå�_�qe�p�"�(�	l&�S��:2nй
)75"���(�K�@�rj(���0��j�iV����B�+�0dHg
��[�<xt~�C��'E�?or�Pq�NɎ��!M��I(��!�gz-�Pq����U��U
�2mֈ�O'`����"9圾
k��e���s' �GN�|���������bBD`�A�x�¼4O���OW�rBzh��Y5�`(M(�f�ΘPg������?����l����VC��Ȱ8-AuJ+.���Dd�r*����w�+��/z���Jґ{��ӧ$-A�6Ńq��z�:y�*P.iW��)(�>�8m��Q)pp��U���v�*���0RY�`f?����G�Ƚ�q=4���������fx�༗�X/�Q�:��$�K�8���5͏�[P��Y��YG���5�X�_�HC/	�&�#�q<C����׈Vj6�U�d�$�#�jf�1�γM�H`��А�����<�������x���a��@�gM0lZ�i�ds��(_�MG���A��wx1���&I�iP�nO���6��d��#.����������w�n^`���фJM���?
%��	�l�W\{@c�$r�^�m@��9�u9d���_����#��/vJ[/�ܡ�@$e�	�f�n�3X^*�5�2���s�w�۬� L����Qj�B���������r�2&�E�����=&�-Q��`��i$�К��Ō\�`c�Z�LWQn[����[D�������8�ƕf��_��0��s"Sb+�W\Q�ܜKf˲�����o� 2��l\fWP��3�H����KhDS���U^M&V��۔@�H�(Qa�2����rЭ<��qj��@�toG�}B揊f�r�U�~8C\<'{�p�}h�
���+�m���u��V�C�F'&P�b�XWa%k큦ؠ�ڇ�.��COȻ��	�G�����ݢ�Dp%R���1/�ʮ�����%�T|I?�@3*'#�#-H�A!M>0��֭�E+�Pc$U���b�2LT�1a�Α�[�-4g�*�D]a����nBP��B_-�u�K�f�PN]jQh��T9��Iհ�$���mb ՙ�D�u0��A�@ugj,p�!�%-��E���r)R�Y:Dv�C�:'i����@��d;2!kO����=���\���-��m(`r��G���Ւ<�����6�a:�&v�C񟋹��6q#�h<'�z��5k��F�Zj����A�����D���qh�DKT�G���s�-�� ��Q�D�l��t�	"���]5%�}n4���.�;z��K·��U��4�v0��&�w� �!�B(�zTB�C�b#h=چ�lȕ⬽���N�l��t>���������h�(޾-�T?��k�9-SX>�� �м��~�S֕<T�R~��y�&n�K�������F�`PO@/��k>pN���VNu��	gmvT�yoB����Ʊ�Dw���d�"PX��nk[�XA��ã�tIl!x�&F*������I���!{jZ����1S� ��(=����Z��4���n�yl:o#kLpCvz���a�{���֌������5mqM`'�
E*�!{�$+X�zi��y���¢?�&n��xȲ�C�H��fS�Ln��ʋ;�}�&&������ ��:�H�F�'���֨(�hr�ȤV��F��>/�η�{y���|�8��Ћ�q�X�zB9���R�ADϷ�ׄv2XE\b��u�U8�:�Sf�%9�^��~p����kb;�&�E�~`���<qŀ�� c�3�O���;�&za�9��E�A��k+5J�~��m�϶�{�dE��a��s22GN�`>xH�����L�)ڪ3@R�Į)�]�S��{�&^��� ����5g����� �8YL�Յ����ķh�>��� �a�%N�$g]�"���u�;����c�Y�	�[z���4xl�B��`�lle�
}�`޸0����e�=��t>� �v�a�*��a��r)�����i��>]�9}�i���Gע	\�5����(���Ϸ�[``v`6?i�>��Kr�%���!�Ø� ���`/��{#�7��7H��ZNWf���&��b}���%s��Shǒ�,H�?���N�:G։���wqCQ�3
�бM�&�҃�L��`*͈eB��D7�����j���A��m�"M�Dq�����2�O������{���l�PJR{��셯�R�sj��r�JK}�%z0�y-ںn&��&zI�����M,��6�it�#��Fk��_\�X��U:d?���I�R�t-�D����6q����̣T�����d��L.��fB�C�ڪ;�#�6b��$
��!�􄤸$����"�ZJ]���������-N���c>����[%dG>�m��l�g�z�3)���t���x�� 9BNTt���5ht�d��^M��t^�M/[h,�����@��|bMn)|Š�1��]����ܚf
*��54G0���1��6q��v��*E{�z6��C�>���54h�ˊy�K�1H9�t�ˢ�����K���&�,���w0\G1ۯf@���{��-r�d ����9#�iv�ԁI<HN�6A��_�w;^p~���d�����UP�����Z;̨g�܋�Nk���ت��qV�o
�Qn[���7�G�,��>���/y�.��$�����M0'2e�.�fZ+��s �ok$���� ��u���.Ec_�ڻ"�ظ��C�"A�3��pv�ܶ�����"G$_���o6��[���
�m��� jF7zH#�������\C�Õlo�$�dZ�ТZ)	�P�?�&�MR��sMHt]�/���!����k�Dd>�uQsy�d{`CL���9M�vē�ϗ�&��߱)��� |   *���/��;�z��o��J���y�o�Y�5��BQf�Klߙ^��'��-�[�??�ho�@��ćs�Y-!5�5y�B?�&^�ٲ*,T�� f�����$���[K�J���p��lʉK��noo�6i      a     x��X�r7<�� �x?��)��ɔ����Q ;8���4���obf��HG��:q�DUVV%������Y-�):�`��ʈC�ј0��|����O�ӎ*K7�M�X�2�����^d�[6���D�m�|,R6=��Xx�̷�2,/Z&�D"�W��$y,N�s�y�ڼ�����)]�Kv�v��y[_�y�fi��yT�o~�l0`���%�y�0&)�r�y��ڔ ��!��y�cyڰw�����ef��^���1 �d�m��7�q������ �q�}ㆤC�d��[1��Qe*n-;ߦi�]����������m��	���~ϚL���5riJ�&P�Ii�!��J
��<���o�N"�*ʑ�2R�F0P��[�&:��Nz����[��[>���gM��n)\�Z.8#=j�J��6��l����`�I������W]�m����U�K�1H����;ZXA�{��f7�=\��!��^�L����y����Jeط��h���U�
֨u������*��H��D�� �ɫ��|��,;�P�yy�>�3Μ�����g�O�g���:mq�Z�\�{�r��5][2*��Hu(���?B^p{���?	�a��"h�wҀfZ���ׅ��-W2�[��桍��� �H�f>����wlI�4��B�x�!��������(e������b,��\v��n�����������fN��V���!���;J��h-זD�QX'���t�v ��{��+�e���y��_LO��w�e�HOL�.�m6��l��h��C�i�j�]�����kb��>9��~{������[������CZvh���Ҹ����E���/8�T<�ު��M��8T<ˢv]	�5M�5�b���5�#m�vO��zϴ���\���C�N@�<7���OB��R8x���t�P8Q �~��?;��GI`���?�>g��d��u�R���� k�P��T���e�ъFÛ�%�}4X�KYJnmňҧ�,5�!Ig�+W0tè\N~�{��{�~;�D?�2�o��ی�J�<F��m�<�,8���*�Z�P�ry$�NԞ����Q{�z�Yհt��i�ox����U�t1E��T+7�nŀ�@�Rc�>��b��[.�fC�9'ӣѓ{�����N��v��"��o��x~(t�B�:lp��15U�	�S�fy6��=&?9<��ݱ�,K]o�UX�
q��$���� z^��V���hO�0BE��J�xV�P�X+�q���I=�LH�$\@ӽ�g��ƌ�I�rއlWY����!���@yaL�8��T����F�-�Ț&�yh����3�p<%D�UT��fM&U�5y�'#�gK��%�}�N7��oma�D��Ԕ�ٛh�'ya�Q�� �?�0k�%����g*ɱ�Jq[������y��_ZmB�d2�/f�V<�Iq�_�}����¬�W�F��xl��|���([���"��,��A�}���E�Gȱ;V�c��i����d�$ -i�p��L��`fK8�Ch��2}GfI�-�b*�(�$��l0d)0;���ka�pA(�ĩ
��2O	���9+�2�|f��ȩ�B��yo�1��i�����2����Y��� +8��% 	x��!U�+�~�j�p
z)���BB��iBv��{��X�5� ��\y���cX��`�%È�4ϴ0k��
Aŋn���&HqR�v��L��5,�ld+�Xܢ�1�C25�-¨�Zq��¬����-��/`7gx�-a��%���ҟga�0�_�^�x�_���      `   �   x��PIN1<O�b>`�$Nb�����QKh������T�*���� �	$RAQF��[1���G|]_ϗ<���/�<�.s`�h���,"��O�R�m�e����%�_d���gzNf������x�/x��_1�\|_���hm�{��C��+Gwu�}� V�����֍�a�]H�GoК�Ye��eß2�m��p1      c   �   x����m�  �s���	��]z1Ď�������Chxz	�3g��Bd^@Pȧ�����2}�1ڹO��&��W����Y��vǵ�s.&�)�D��b&جnf�֜�dr��~�[$������n�������n�9D�kDHO�Q=�,a�k��mSIƒ�3��Q!S
B�uaB��������[Tg�      }      x������ � �      8   �  x��V�n�0<;_�C�[�����
��X��ƨ-2s(��{��$qn`t��ݙ�Y.����7`����P��m"�J8�WmUW;�7�`"��V�_\�w돺�NH/���� S��DD`S��c��꾲�x�������vCC�=��!����W�׊9:@�&� �*�Z��ź8/Ɯǻ�q�9�zƺ_/�և������[6�8������N8���w��3��۴�󈏴*!0X͉�AGPj)ҚL���|z���������)gD�@Tv���!��(��A)�mT��4�?iz��^f��>~b��&g=
���B�r�H�hS�"��6�`�m�M�GI/)���S�S_�4�h����;Ī
I�Y#�b���-Y��#�����u���@����Tx�Y@Ֆ7�,5�)jȂ�b3Q�!�];��hh���ٜs`�K���Hc��Ө^A�8��n�2��d\�=����:ik�2/��`��ܡ99���LP{�[E�##�� �1��2�Z$�jt�`��&I���5+E^�r�~ގp3�o7���d�hg�2*^��&��\_�@�T�	�7/Ͳ�N��K��M3�rb.�.Uょk4����./�ơ�P��nZ���^oǌ[��>%:�7��E��O�92�6V�J��F�s��H�۴
:�������N��eTz�8xx��}?�E��^�Afg$�L9��'/�ȳO#�9^����`j9�      O   �  x���ˎ�0E�ί$[�����#�4��:_?��:J0��{Hȹ���t��i�0�с5f�I�hl�J��Z�����q٘vqlK~\��K���L��l �1Ũ�&���p'Tb{n����,~�~+q�9{I,�q"x� Z� >	�	��I�p��� ���A�ZM�t#LzN*j���i
爃st`zr�LY�0���F�AiTH�3�`R���J`�f?���x��� �!�rn'���zsH	Pۨm�.5<��("�/��5�m��})Y\7̻����qH�V�.�-3�����Vμ��T�֯X�9��8���:m��rgQRZ��boC��$<�_ .�mſ�U��+7Q�6��/E�<��>�����R��^��A�O��>��.�#�]�n���������	�O7      �      x������ � �      �      x������ � �      Q      x������ � �      q   R  x���KoW��ү�e�`qߏ�vӠA-�M����M��#��/%�A�8�j�H��=<<��Ⓣ�"�NBA���2�R�b�}��>��|@"9v9�M��[0z1�DbkR����~���矆�y:�n��0�Á�[�a�(���#=|{�K��Ā�zM���%��b0�����n]&�0���u�~{����tf8�9�ʹ��U[2��!h��<4�Ub��w����I6i���E�1D��=`��	P<��)r��Nnp�6Q�P�B�!�SS��P� �@��l�Na���Vx�u��K�h������аz`[��4�D�~Z�&r�t�7�jl��4���8I��g��{�竇��'�����`��2?����o���'�*I\I����݉�L�ٺ����:~�-�4-�V�^��7�8�Hց��@E�P\�V����f�uy�
����]~�ũ�w�~1�K-N������5J����L����u�h��;ug>���q��q��M�n��e���͓����H����!,MԿj�U�2����%�-a�H���zZ�Sp��͆�2���^�fÕ
�!���@!$`�s!b�@�U��C3��4�� �����u�\"�(}F�mͶ[-�ՇSr8꾙��i>6yD�*�Es���(�;!*���	2;�ӳV��Ѽ��o���k��3N�# ���D������L�Y4��J��UH ��j*ɗ�{|B�K4���['I����=6��	�4�07��)����|!����F��Nh�
i�ߍo�B1�}����o4���h4T��b�m�4W�3��g4o��Vh����~�/,�x      r   
  x���;�#7����ᖆԫҦʦLC�b�k_�^`#�=t�&�Ε���)̧s�Crj�.eO�7�J�0 	���S)��~��or������|��������W��0�����Y`���_���8�����o������,-��z�T�g_�:)�?S���?n�����?o�5q1��k�����E�j�D�5��[b����8����u<����ho^~��o�v	�0�4JtX�4
H���q��fƖ��~��i�ֺ�[����
ٽ����` �Њ���%��A"�启��064��Z��j/ЩE�B�v���Â����uU[���~��Āl��:y�]���]nZ���q��_��
q��].�M�2���.�9h�$��U_|��>��
��uY?~�@�>��>��,�I�Ї�.�1hj`	�Q<��XZ`p����y��@�6��LU� ِ�Q1>�����|4��UJN��R�����q�+�fT�LB�Tfq8����,Pgy�۱"`/j�	,�.��jy��[b��@�umd�/RD�E|�J����P��j��O3��xi����\�UV�L6�0��k�g(<S̥M��
����k��Й����}�
�1s�������jk��k�T�����B�v�X�i�@W�-��#�E4�~t�_Z^{�Tz�����_�|���o(�{b� ~�h[��#���}j`	�Q<Zk��,_�*�CO�ه@h>�0���5�C6d�z��vm��      �   :  x���Kr-)��^�����'�`��h�����*��+n��3������!�j�V�����6RI�M�'�]�^Y��vG�ih����ֳ2o�Gt�+6S�yT%-ۚF�Ye��O=�Z�iC�=C����̗w�*m�<�˪�I���u�kk�9���U?���׻X�o^���(��Q׊絳��'�d�(^�Yo����g������V�3����]��E<O-o���Q�(-Ֆ��w8��X���rc��:g��vz뒆�C��v�<;Og�o��\��C�Q��K�Z!��x�ڹ��*bAW��X�UĂ��y1WY홫�]e~1W�K_n�=�U}�Ei�)�f�j�^�*bAW��X�UĂ��y1WY홫�]e~1W�E)�W��U,�D#�${[��ԩ�UĂ�"t����]e�b���3W���b�v�WG7���up�UE{�7-M-� �h�JX�_%,گ�WѼ`��j�U¢�*���h)b���b!�EZ�q�mV�'v����]E,�*bAWټ�����UĂ�2�`�^��f�֎�.)2���R�ݨ������B�q���-��*�Xj�΍�}9AX��6F�r�ļn6Y�]�g�)c�X	e͒��1%���~d�w$߭m��afu�)G=��G�;.�y�ҙ5�K�ט�_��2�}g�k��hҺ�A��Kd
e�tv=G�������S^*;?��t_I�ܐ �ţy���57�������,+���/����o���Dg䍷��\�:�Tw���b�-�/v���>S���������H�(E�0H/v�4���0?�U¢�u����E]%,�*aQW	��JX���p�n5�{>��B��^(�Im9��RW��X�U�b�"t����]E,�*bAWS;%��e�ؘ���h���ZK՞� ����]e,�*bAW��X�UĂ�"tU=�c�$y%nLvr��/����R���UĂ�"t�����]E,�*bAW��X4Wc?޽[����+nl�I�Yܲ�]�*a�\%,���s��h��U¢�JX4W	�Z�i�"����.{��������j��"t�����\E,�*bAW��X�UĂ�������?��I      s   s   x�̹�0�����ĳ�M����� �H�yWB�6���vE��r�O��\{A����gv����5�Ƙ���<HMB�`ߦ��y�p�� s}�xc�	ʏ.�4qi�����L%/      9      x������ � �      f      x������ � �      P      x������ � �      ^      x������ � �      :      x������ � �      g      x������ � �      �     x��XMoG=K�B��|s�X�(
�)�4�\8�-DҪZ9��})�*7Ew|1�pȷ|$�%��[������1@�&��6�����n<I[�8��q��X45B��@��@��J���v�8]L���d8ެ?�=o����y�2�y����(7��t��fد�v���7'��v���`�o����p7.K��(5z�r����Ň��7�~To������(m��X��/����M3���HL��6�م�{l��⇻�v�+Y}��n���6/�-����_X��$@���t�Y�a�xK_V�����ns��eG_�����I���T!:�Z}�Bu��O�&W)ݷ�w���V߻�pxخ��� �Շ��L�F�<�?����/<Y�Ņ �c����iIJ��mï�/N/��	��N�;l�$/�����t�Mk�F���һ]��;ݪ�������I %e�#��4�9��^C��@c�N�pC6hƓU�L�*��]G����l2�˵�B�g\�$�ʟϛ&�i~�eI����6i9zwV@j�sUK��X���6{��c��֑q�B�x�E �'#�V�nq���?�y�hCo����d+�V3�<��=^�9�nA2����ib5]�H�б�jR@%!"���2�f �E�5<�hmtV����'7����X%�Dx���K�l]�$Ĉ6S��
N�ws��\�p�Yg���&�IU�1�d�}��p�۳�;5Z�ZX�`V���J�^���.z��h8��9̝+%����VY2N�cP�:�'E{��63��g�f�}��"�RQL�rVæUmՙ7�UuS��63���m331��l��[�����Ĩ��9Ry̽D��63��o33���ԋ"
�);�����L�؆��Q��63�˳l33�I��sb�M��#������?)��и�<>�ƝKZ6�I����#
!d�H��=ri��E���|�vn'r.t*U�q��BT]���P+�U5X�)�W��X|���>o3�,<tN���w,�5d�
���r����s�      �      x���I��J�%���+�89I_Ԃ�DI�Hq��<S�DI��]73�*�v�
��zы����n47;v���R�) �f�!��H��!���YDe?b(�1��$����81%�H�$���a��l����e)���eH�����M��~d�u-9mF�ռ����I&@AD	 HHp	E��qa��,�;f�4!Q�N�<�cn3L*�,��1$9�'糘�S�'9� 
�m�D������3r^��Agt�TN�"��R��aI��B�W�ٱ���i�~?��[��0�0�y�#�<Ł@�v��� ���������mK����1{F�8P$S�$�]L�bĀ���!��oïH�,S���4���<�y9H��)2f��	B9� B���';�<�����L�S��8��$�9>A��a~�+�5b����Ф@s�� �@A2�`�B�0q�d"�H!�񊱹@B�e��r1Ixe���d��hmrN���˴f?��θ���	x�#P�r��~֔��.� 'B�daB���3�K<$à�e9�J�z�I[e�2�`(�g*�,�Ꮚ�HEB�Ӝ�4%�iF�B����~:�x�)�yH�q� �ei�ݟ
a=9H�bL�9c���,�IHa�e������0�a;I:�pF�4E�?l,���:���G9`4��>������ㄖj���5�B��^.I���y��i9c5�-%$Ls�LR.G9�3���6�߼F�S5�iS(1M&8�0��a2��$l��	v�$4@1	��������,%b��7���P�ׅ#q�%8'�d��H3�Nh����Hs1S�Y�cxN<8��$r6K�4���3���� �/�=�b`$3��b����7��劥sQ9��0�����|Nb�$(Ħ�o�?��|�`ؠ2l&'
8,�$�uł(�*��/�E�`hVĴ�!s6f���X�p�|�b������,����<�c�� �*���� qT������"1N�sI�f2�[g�� �!��D�?8\Dq!M�}�1���WU����c���T�q�J��M��%���~�\�X&evv�R,�s��hC8(E)��y1�s��ڔs�L��Cws��YaeO'}�������8��E�"v,J6%s�&8�����<���jy��q-�2����_n��g㜤�(^�@~b��t
I
Ѹr<���؎X <���<,�i�OsUC#�q��c�΀�'`�}�%;f���A��,IGE�5iAd�h��#tn���'R0OU����N��y��؂4"H��'���ڲ�`�LF�lV���R/T'rc��+\��	z��ʌ�5��y'8=�	��S��J�ƸgA���Q�����9SB�%(U>�'���7^9z���T����L���e��C����DqK	˲3���A�y�}�����30�;?�`1��n�^�3�1�%O��C�q�i���''�z���HTW�4O��˭��Xaf>�C�Z���a����s��ޟ��5�ԣ�vP%[���Y{z7*۝5`��h]���n?E�d�{�z���9����A���"Zv�ku�����3�w�h<VSOJ������������U�e*zMh�����1|F���&�����S�{�cq\��D��ѩ|��5��2����}o����!�ս�	���%�=���Z�J�Bd���I�cn���L�=+cc�p��7��"5:�
�{�};;��лe�ݝ1	�{2��^�a��1�@�D���ִ�{�Z%zі�QV�޹]���?���F)��j�T��;�z^M��ۊ�ӛ���׃]M�|S�����'zCMx�������J�/:}���{q���F;;	o�K�
HsHɖ����v�f�G�a����g:�����q�5C]
��f;1S>��b��%�����wp]�#���1=eW�5���5�~=2��������+C-�Q�07t�J���#���Mj��\G�8�GN�$�3�-Vx90�u�/���~�c>�(9��tε�i�F��lx��Q:q��J�L��]���x/�)�a����M{�tN�m�}"����k����ѦQ���u�^���6:�}��������|��*v��J�����~�̇p:W�5.a?�@�VὊ���o�6ܼ@��hɓ��^;Kbd��ɮY����~�[¼8�������On�:���8���
�{�<s���(�Tx���D��׎(�Y�)X� ���r��1��֒0�^
=+�/˺^�n��^�W%����Ӕ.pgYz]��3���p�������{q�5�џ=G�I�^W��9�G�(ʁ��NY�xvw�x��x�`�!!��
"�} �S<pbx���?6'�⟁Y\����$=9���&����L?Cw5�.�]�P<R7�4=�`'O�%�~�zu���NO(�e���(�Ӷ�z�.2tԣ+���/�0!�!�(�����X�a6�hf�H�1�����J�doo�~�&y9���+ ~)&Vp���N�+?L3�_4�L�ʫ��o}S���H��"�q+�ӤJ�\4c�T�(Y�g]R%�l��j�U߶UU��{hQQx��eb�k�	��9����L�}��?_��?&���1�B��e(�ѸbV�5<�'I�AV7�����`���6}�-e�o�F��轚/����Ӹ��E�:+�D�(�`��fe�+�G��g��%F��պZ Yzk-�-;�<i�Jpj�?W�����b4>�	�&�e��F�.��9�Eq�}��zN]��(�N�>�bX�XN��qK�{�����s��H�[��xz~���T��k�+F�u��hm�K��A��{\�m~�
���l�s_��5i�f�	���Z0+uJ�3�~L�E�f��P�{S�����ʢ�a���=<M�M�v��z[�_~#H�s��6�q��3�0�9ɶ�b�˒a�/ּ�TeoV�NUѲ���}z O��j����P��CS��Jj�<^w�3)�Z<�N�\�)��[*�o�|>���=�&�9[��#؁�n����bΗ������<�,]�c�ej@`b���0Tv��!��J"Q�n�,��P[��>fow!M3�#����8��8�Ǫ�^x�����8i@���kS�r�껋���^�����_/�܇�A�y�X�����~���)`_)�5O�S�Y^�ԥ��LN�]��zK�&V���!����o?3>��	�k�Ṯ!V�����y �},���bJR�H�X{cyÌ��N4E�Y�7��Nʰ����YD�T�cnKgY
�'� ��\��/���_i���B�6�?�����"�������PL�%�X��ڰ�-�Öݏ�+�1�:%�w7�4��4IAA���Bm��ke=^)W��.�:{�W�T!��g1^ށsd����l��7���~�� �kV���|9��ً��t��g���bV�n�"�=��y�h��(r?OQ�,)��U��nBe��1'&b�����5K���X�"����� �i �QH��̀�~m��5��eͤz�⼆�)k�Uw�;ƾ)�p�7`���Rn�F3H��;eC�ރ���fT��Z��gw3�Ğ�c�X�<��:
���n킠�Q�.����u��3U@�i�M���Ġ��cs�獮ݞ�D9�m�]�s����\P{]�M��X�����v8�ցyבA�IS^����#��*�שM����ڼ1���TV��Zf��nF�73�Y5 �HJ� �>�9�tp3����ԟ&���Y��K�D�H������Y�w����Y]���q��� k��(�~]\F=��6^-�Է��-����(�^o��HÉ٭�i�OZ6ޟ8Ռ���)�/ڮ�U��]��p%��^=7��݀�[������G,�a^W��v�SlG��f�7V�:>^�,������U?�4���W{^;k��qGH�0�(o�J���Z(б/ӡ��ȼO�#x�w3�g�T���X5�!j3�.DW�����n:���R����/�L�eߩ�\#�{ɸ��}��M/�y    �tn��:���5�[��.ȵyjv���9�A���I��z�oi} �K��w����`E|%��i��s4�AϷ�'����T�Oo�?'�}l=�֫�d=����2����py?�X�#O�*;?>S�e�� �ffR�9[tNPo��%���tJL5���k��b>�7y�W.���u�H|�=���?��1�?�{�k�-�jق�Z�7T�5V/�Wu-�hA���k>�s�cܞW��=���N�Տ `�}n�5�����m��tR��DA7��[ߎ�j+�2uh��i͐,z�N<��(���w���+[�9|H��)��.�6���������z�8z�\���L�Q3%��D+����|�Rs�ԩ]����35��4�C�ʕO7�T��H��l�+|�1l��`��ˑ�3��^�'�2�]OD��l�n?����Q�����L��(�]�^��؜gڒBJt��cP�;E��W��� |��k�ݛ� )^n�
��
c��4�+���wu��au`q�CU��F��ؙl��}(�=%wV��VT'�����/���FSq;�NQ&��]���W�ڒ~ק}<�Y��;���s�7���)$T���4���Y�j�H�L]��{��X���ڂ�yK���⒩~���x�e�)#0���pi�s��J�M��s�B)Vn%���j��t��Qq7��>k�ۿ�� ��K��K�=f��D�Ö8�QIN'��U�/��M"/�v�	�R)�G�q�8�% ��I���,��J���;1a̤�� �J�ۥ,&�"�G� 3�8��䗆�/��Yr�OVN�l�xzoT���w�rg}_�g��u͸n��I�pܫ�<~Z�~]F�p�ǭ����6��|{�Dt����I����9��"J����
�88�p�<D/��T݂���[��t��E�ͣ����v&���1'h�ky��ٕȺ!�_���,3�����N�:>ZMT�I���yK}�dGn?�OQ��s{BU�>��*���;�|���k������9f���>==�,�{N>~��ُٚ`h>
�:����k �;�Bd�����C��.��0Q�{5�R�/��A�ǌiע*�TN�s�	V�ʲ�yȵp��뮮����Q�Jt�=^ g�Z`m^�9neD�G�|��0P�t�B�T�}���y><U���Z�����$�w��T1y��?WAI��[�]g,k�[��zh��/�sa�>	}��A�]�(�:�]dm_�����΅�u�Y��?�oI�����Y`7Uo�!D�|��Zl�lW�딨9��̲����}r�W��aiL��O�p۵v��K�s5��΍U�A<�BHǣa�D�>M?�U�	��}|U�r�p�&�OH�X뤀#'$�C>r �q�#��p��I�s�K ��� ��P"���,�SZ��u�M��yg���:�ݳ��}�GJ�C�Z
�2V�Lߨ���mg�{Y�4V}�_�����v��/͕�ra��t>+ V~��>c[0��׹5]�.�����}��;���
ҁ�A��M�+��>�Wy��g��P��q���jc�ruo������y��-4o�
��`0�Q�}��a�*�
�H�7�q�E��'}��ܜ���|Dk�k�8�Z>��+���?��oa�5"�����;��C����2j�<-`��6�<��&y�����w6g���꨺:SέO�p��,�֖��=M,��r�XQ�eO1���;���+/B�
�lF3����V�?��s^+�8�%����,�MNZZ���r���g��
���7xŐ:(X��_����O]�,-���Za�<�b*̻Y�͵G3R^��7�p��l�*Mʢ��瀒�WٝO/���۽�\�e5�W�v�Rk���pڙ��-OL?R8!�1�Wp�wGǉ+x������Vc#�0I�f?N&��9�N��U��,��FU��и0U_�sZ�(�43�?�Ŭv��5�ٞI�����^`$9!Qwk
���>����E�8z�SP�:!�!�t��.mnm��U]��|���Xx�9��v�ZKLѯ����������' |�b]O�LJ��ERd������<c#$����|��� �D̗_�qF�b��y��;R���׆��<)3�@b�"����C��xN�~��:v,��>����ߖ��#I+��r�y�sS!�f�n�H����^�A�]��o���>;�%n0�5_D�?�Ҕ��Q�v����/��3ZR��4����%��/-�b�i���~�c���iC��������ߍ:4#���to��5��
.��Tw��a���08��a�{����ō�$�TE��'w�J�]S^D��wO�Ƀ�c���R��R��ϧ��J�skԔi���N����UΕBw��+�j	�;wA����n�{[^�T"�bI���������v7��K���'��N��U׎o<����8"d{��&kR��o]o����8�f����|����6�W���E5@��
|��R̸�s+O�n�uw���J�n�R/���s����R�s��͵��~��]zN���8����f/��KN_��Ż<���y\����H<jv1ǮHYq�5;z�h����.W3�Kq�[�h�>p�q��~�U�D����aN������T�wY�GQ���n^֎�Ȓp��މ�����g�w���=�%� ��ʧ��Y����$����� x�5�.�R�^��by;�Q�Z�F���!O��v����$*`���*���3�������]{�E8%%e��XU�)�(N��;�$
�]���s�����͚��z��ar0��S�?��j��&�'�$��ǂ���F-�e��)s�X���uz�xN�$Bm*�{��G�,�Bk���E+�����?4c��g��%S@@\y��w=�Y�ҝ�^6�������E�:��G����h���"O�4j��K�q��P�CB�z�k�>de�*���l��@�;��Sp��S&X����|P'�8���q�E0�v\������
��7�>5~�I�LP��r���t־_�z�����o�ICϡ;9�}/�Sח:3l/��g��=������1�)��{ŉ��(�)P�y.��T��J����H�ʱ�߫��e�b��4�eiJ����2���� Kg��yR�yHbmF��c�q�؃63�׏@׎���9c�y��޾�@���i�c�g�Y�j_qg1p�p{�ǁ~q�V�}�e��S,�/J?!�@���4.ǩ H(�	.�qB�1#и����\Ǣ( �T#�<a��0��eI1� �n,��P`��IF0[)�Dː)���0���צ�'����{�_$'�x�'1MA�[� �9��<�k���y
�!�`Ɛ�P����Z0$���q���1�A�Y������Y�"~^�h���[�$��^�H���p }�E�{g?%�23I�_�?;��F%�(壑I.){7��VL����]f;܇IkM��~N��O6N`�'��uw8�!���l�,�zV�1�*��@-�wf��6�����KX���8g�|/��4�j���7�mƛ�ї�8����e�^��#���b��^	�kE�wgdT��?|����9�#���`��PN:d,�8��Q�E�=�h�;��aJDOW,y��
(s2��m��Vn�Z
ݢ���dӣ��u[a�H	�ʅ�����ق�\�N ���=��?1������Q���C|{KO���͎c�.	�t��Y��Ko��:uJ�\0΋�4���ҍ;�9��}$Y�;zJ��4�߼n=ޗ����#SS��7�U���zwt��ڽ�Ӈ���>��뱈�1s[Wz/-��F�����\��[ ���M�I�h��c(�TV5�cM������}���rS�M�e]M�]�}�h�� V��\ ?J�1y}a1q���'�6Km^#Oj�a��B��=�\G������������mw�29�̄���AQ�rba�[~v�O��̂	Qt=>�G/M�Iʮ��7�E�:�G��F��p|7���<�ib��.M���Z�P�^F�k{�=�&y*8s�J��}���=l�z��ć�x�t��̡#�O=U�W�A���� a  ��q�\�CI�
�擦t��NV�䳾u|p�v �]�՚���{�˰�R���B7D�|�f���>�}3��w?�vr��Ĩ�7�#�e�鰗���w4>z�_{�y�P��wU�[�D�C�Ȋ1��+��(��:�o;����2d皾��~TK<&j���uu��/y��K=���]R���p��d�bq�s=��B�bw�j+�(�J����iK՜,%����<ؖ~�L���&='��3�|�0��׸�3_�0�+]O����[����z`)W���en�3��k�����n���,�f�9���y	�":��-ZZ�T'����ԝ��Z.�g�p9��ss�RK̲u�{R��89�O?s�Jkv�_ ��|rbo.�����۸�hF�a�s���nv\t�I�z�zon�܉Ͻ��E��B9xBs	�Q�g�ɍ�M��u�<f�P��)E���x vn��>�Z�m��ܝ"|Ir��v���`��/�i�A�8�V>����&b���!ٕ�ϓ`�+��Xqgޤ����"��5��:��=e�����H�GB"�K=|O*�e'�RRd��0!�l�Ņ 6�E'B��E�?\�b2:IEȒ)�����`)&lLQ)� ��_���sl������o-������̡f�إG$��q7�b֖�#jWF����х���.y
v�O�.@��zo,ki��7��	E.�zG_�fk	�A�����<G�,&_9���&n�!��W�C�!/z�6�ě��Ah6���ͼ��Vx�����:l��Go�OU��@��4���� �O���7u9�{���w�>����\�MyT7k5ڜi����l�{GA�����lz�Mt�j��uw��2�|g��<�G3[<�f&��������������@�Fs�����@��'�h��������	qB{~���b���IA�톤 �4{d$�c�̙���c �A��g�K� �f�8�g���R�����S@$�K5k.
_0I�T�&�Y�������Ih�:��V-X��<�0��3B��<�k�~u� 0�P��YHa<	�� M!�'��$��,"KQ�&�;O)Hi��4@�p��최��8�1b�C�IH��b%
S��G�_�a�AA�����^��A���b:�2�)������d,�����ABNƬ��His����� ���쏞��{�컫�c�&�]�)�iN��۔��
_Wf	�����`.G�o�m�i���8D(bgӈ��l�〘�4��� ~��8��)G��eE�����o��_:� �<Z�΄��)���dd��V��t�;�����e����[�9���������dB�[�/�i&��(�Y�~�r��9L5�LLh�E~�����O=�%R "H�I�cXxL�8�T1��߶�j�K(�PDxΜ�޾��bL�4fi<��vſm�����0�ȑ	b��=�a/P,Dbb��}v���$xԜH��i81��=��-$b��?h��9��Q�P��˥4)"
Ʃ�b>
��x��X��dq����*?��9�E������(�	��쏆J�����0�!
 ��o�S8����p��o�5�����q(C\̠�a��m	�;�>M!��wS��"\N��'���,�S��B����$C8�����\X2N�t���O��&)\7�\�����@\!*a8
����+P8����W?�����C	����e�CD�@���a����1"!c�4����s�p�s+C�J��3�$@��������C��L�S�L��(���/�p	�I)"L}�C?ǥ?����8b�/M��0��냀A0gAS������??��L�Z}      ;   �  x��YK�,�
W�>�����	-go���e݊P� $Q��:��]�S[�ƅf�S?�S�_�歃ڭ��^�����]���niV�+�+�o�:͵
-��:��Rm�����;�5Zǜ������y7X]����p'�����v�����n;u&��!)����=bK�VZ�N[��mԯ�U&�r�8�Ĭ�ݱ��S�ZΠ�nw��^��U{Вub��Ō*3����btk�,�^���_-V�����*��5��ʒCKg�n3ׇ�6>	��$\��$�B�{�u�n�Q�.�Ǝ��=�={&�|��5gAﮒ�=@�ғ?�3X/ި�]��;�*/G�L��:��yu��(5�,pŊ�\�1ٓ|��"�����`�Q)m�i`EOV��6w��w�`��T��!�G�$�Ά��-hc��;o��zo�8�Ց����Rg'���7�d�˞��_^wzP�5�v��A�z6/�@4�ց:�]L0FƪTB?��Xb{~���)#b�ޥ��u��1'p��̬���@�=6�:��K<��
w�k��v��q�H�������ugڻ`�:�lZ�$P��W*�w�s�-��P����9s�!;9�q�r��
�k�2&�61t�R�zsu�Qb�oҎ:�R`�2����(yI�/����	�07�8~��Y��/��y��]��jo"W���#L��:������!Y��;�6���k��� 0E���r �u�����DZ��8���&e!Y#�!ѵ1}IUY��4���/H�K���d.��V��@�yA�������+�ԃb�����|�P��N��MkU���r���r���r�'t�����u*����������N^�F�jҥ
� �M���1:\pEvqե�����`< ���6� ��8�r�WY@V��Xo��@BXJcj���U����pW\�?��r��n'�푬Sk
i�ȱ]0� ��Y��9<�J6g"%񞛎��	=�J�@#�!X��d.��T��>7易X0�2/�8�g2��a�5�Np�U�P���'���/#�:��Rg�HO��#f�cگ�SMX&-����co�����:�_8����D�6N�dǞ�"�L����C����<7G�i�LS��0:�R��Cx(r�~�W'�0��,�֊Ev�$�LG�{%�xH!� �P4I��;��v�2|`��-��n�=��HV���(�`h� ��b0������px�qz__x8oG�xn��&��I$�cA��X��R�a�l�d��.��H�i�٭F���W�c��*�@9$
���
�nz��B,�fx�G	F�vC��!�ۣ1w����P@�8�ч��o{��ńم@of�L�aV��f� l��9��@&�4$�t���Az�]ao���f2�4����H�I��h۞��]��!&\L�g�	��.���7���0��N��������� �ƋFw�[~���V�A ����C)z������V$�����2"��]
)ł��Ͻ����: r�,N0�z�ܢ|���⭉�=��k�ak�b�/���s����c7le�1�VyòJ�����ĩ�h�RO��v�V/ғ���Y�.,Qp]2��n���B�%�7Xl�:���Q��17)�}�-�hѪ�j�O�qn������Dl������|f�QL�y<z���/�Y��b�O�������({��])U�R��)$��~)�"����W?�*���cG)7���rHG��-���e���O��v1d�CJ�T�N�$�cfB0���$�\�2�)����\�393��J=�ۥ�3ջL:LNpb�!ev�R*٧�RI,��@��X��+�W<��4C��^����,@X��H���AJ�zv�DJ�R*��)����R{Kn�2[������__-�K      <   �  x����n�PE������u���`��c'�2�Ol�C��{M}L:�tk�}��fs�!P
B@��@�V ̘&�1"-��zY�mY��)�(�oQJ!��Y#,��Y̧݃�c"8�⊿Nr{�����ڍ�r�X8��.���m(��<��YU�x�9J��N��%L�ҺV:���l�+��S�-il@ҧ`����/�Zn��B��];�p:��8��φ4�h�r/�sYɝmlUn�?>�sY�.��B�"N=�C��*�|�-ԫqԹD��sz�N�����z
�	|�
Z�yJ"�2K��78N ��%@3B�XY�f9����]>��+n�~'뇳�{���wl���}��L<(���ǅ8UtXF�!��i�gr�����y:��1B,1��TL&�\�d]��H\,���'y���|���? cH�g      7      x��]�rI��]�~ ���e&6b0B6#	Ԁ�P��f,	-�����{�DB��މ;l	�����w�d~������\uN�?�NGݓgw�_�/a�E�_��;7�.���ng0<��ޚ��jn�����~���o�WA�v�����p���Q*��.�?�{��Q��b���kf�FX�E����<8Y�E0�aj�Uxs����������A�;���G������r<:�O�������?��{9�O&0c�Q��U���&�i�~Ը3��;#޽{W��z���3I�����d�-�@niJ
R�u�'���䆃���H�VBh걳F˥��`���*��>ܛ=���j)	�5.�u9+�X['�����p�S�>\��F^5�*p1�[��;����t�陆�i�d@���#_��`2/5&6���W�7�����4����`Kq�;۔��qa��� �$AIl#�Z,��&�&���iIp�Sb�tp��f~�7�Y}�̚�Ȥ�ӟ�'O*z{7��Ŵ"�4*(*9-�&�BF��Dc"�wȩ�md�H�	c���k��}��|<��z���3P������hx<�P5�QA[ɨ�ݷ�c���^R�!<[d�<
D�0�VH�J~�?TL�.�}X|��7���Lrӊ��`��������!��s����S5I�>������ػ68Z9����O�ՋHh%��P"��Ld>�-�\�@���G2�?��V��[����[����_&d@��4�0�Q�$RԀ�w�#j�2���֛v��;_�SJ��=�$�cD�(0ʰ�H3ɼ3
G˅��En��S�Zn"��'/�G����٠Q�~�au��� �;��Rl=1�z��� �S���������dڝ^L�Y��G��
��w���ΐ�2�x�	��QD#"�
rs���O�T��g5�V0RK���j�AxR'!"r�9C*z�L�`�;|�k̋�����v�����g��+3Vx��s�$T'���˗ԍ��ӳľ��ҵFِ�z����t0�_���c��w=_̮���I����o��fvs��S�a ���F�����	��KHtn�5�$	/@���+,�Kx� ��b�r�<fҒ]3��L��Mv�%�U���Z��&m�LD�	�}�����|0�^�o[�yE�Z�on�{�5s�(�Y�?�N�::n���)�.p�f=�^9�UD�ĂVi�����p}{���)�~�G���!k.��|F Cf�����P�l�V���؇�����,�\��e(+���OԪaK��ƞ����Ǔ��ʻ/��Sq~y�nučG��y��>�K}T�h�Ġ���Q8∏�
bbd�KniD!
�PNKRjk�����i���[�Z���Nz���'_1����zO~\�S�w}��Ѧoةv�F4�d�K�0h��N8`AҘKL��p6&R �r�k�B4W����C�$�{�a4L?�m��� �������<��W�.�@o�;�p7X�й�_¢s�mMt��,�q����2���}�6��:�ݚ��U2i/)XVD�L@�:.b��ie�LS���"c��.�=P���X���t����f��>��n�R74�2PV�RIV��u�$�2;�`�e��!��?�|O#�T4�ɠ����;�o��u�k�4/HE���@J��*A$$��{�nq�r~��[2n�/�=��ϯ{KV&�nTG�ds��u)�V�C�ɀ�F.F�D�"���a'1�QL0s������#so��o35<>%�T��.�5�]����e��x:8N-���^º2;n`��� $��H�L�L�'�A�퐠&*�~�6I���H�-� "��OT�8�H�X9 ��j�3��������1���u����>]���Py�f�3G0��i��WLU�	*��Lz���uԾ{`�j!Q�5(ju��h�`��9a��E$����m�5�k:����m�D7��}YϦ�8��^��TL.N�'��_`�y�?vE�ܭhY%t-������qQ���`IV�  �!9Â:d��Af!���q�˂cYB[GI�7[�p�����j��~2�;J�MP2@�I��Qo �seT���N�����J������5����IAk����j[�vg��k��G�
�>�!�� �AM�9�
�LI�ӄ�`�������5���fTs�_;񔏯���������x��/7�6��5w���ܶQ.{�=�ڵg!h@�%N㍲LQj%3L�H%�c��.APU}�@M9�J�Z9�����5�����hz^�Ӆk5j{I m�^kϤvySH!�*(ɚ��`.�H�5�\ut�BRC�)6s��G�k���.�	�5�P�a�(�A�ù��̛K�$�9��6��`r,��R���;737��3_w���W7f1�v8�
I�ʲ�h+�g�k��"!��l`��ۚ���?��/g�� ��sݐ}|:�\Ƕ�Yv�%{�PU�j7Q�	e���o�^QX���^�򈶀�b��c"RZ�P1�DJ���SX5���W3�R��s�p{;_�wW����l�7ol���&���G3�'��R����F\a��0	��W�[���Η��ᶓ��U�O��(h���Dp΀{�"jH�	F����E�2����r�=�g�'_�j�~�L�]h�į]�"cp�"��1�a�q�ʗ{���:�˶&�֣X��H����"(�E#��)X�u�h��nW̮��N���X)��)7 V�Y�PV�ǑsB P7Q�Ǵ(ZO�K��-n�V�x�X�Vil�1���_g�_;�t��p���
��@��̌��g��^� :�Բ�]VG٠,�u�����e�:\�+�����e���&-���̀�'�R�P�H��l�X�]�.������Q��f�u6�����=�#ϙo�
�i���AK/� "���3+�:���yn!mzGʁ�V�Zy�|P���S6�t�S��QO%���o�bV+�BŠ��� ���c����{��f�\�H�}҄�+�	A�g?��"��z��,	�2�Җ4��r�#��m����0;�m°A(+�'�[ͬ�)N\	�tڐ�.�ݔ�>��&+���K�����y��i���r�)��_$���<=���~���*��,��J'(F�:�҂��3�fL+�"��9S��^�d���g]/���"�M&��ڹ�7�k�|�p~!J%�2�8��q��C�(��"�xj,�1�e�k�0-�E]�LjJ/8���2�"�<��(\���D��_�m��
�����S����f�,����ަ�Z����e�4��}\���<+;���V-�t])G�
K
�Y׭�_<��ߖaǝ��Y��t�����.��L�( ����K���5r��D<K�ʓ�ro	A�)e��#����"��AM�57,G�P����Rz0	H�!�0$���sK���:)*z��u�\0���o�fv�R(u$h��%˂)��d��#ǐ7�X��Um���!Y���G�b
�-�(L$��<+�*i�W�P$0s�1���ΦVA_����CiKP*�^�����6E��+��rg�fv�N����y�b~ԙvߟ�_�kil����'Rl��N0!�DR�8,�
�e���V�}T��j�"�DV3�'d�3�B�N1�"x�*մh������OG\�������g��y<�Y�{��/k��稄��#^��A`KL�K�k{4S6�+5�R�.2Ba:��9`�L��d�^���Z��W������I�@,G.��~����++��	��̽[�[�<��ԥg��fM*���Ҫ�g�oH��m�y��ZX�cq<�m���!��}D1"�9FE�@�Um��AS����t�;�O���:?��b��Y[(M�eZs<��?��?�~b��'��L&�X{+��B_&��5� ��!f@d" +�8��mtLj��E��w�{�pB jS��߂�� �   �悯�������GQ\5�4m���K���W�Bd�E.��<`���i�m2���`�7��Ee|�f�j\?�3�;����0oSq��y=q�9�i)��E�v��'2��$[,�hE�0�c�\E�($.,y��4�[}@'y*�i����l1o.��Uȭ����!��T��T9@q����)H�x����b X��ۛ���7�o�T���ۋ���%i�3RZF7�=V�;��J��Q��6CE���K�9Y|�����5T1���;y"-�t�p�V�,ڴY���N�����JQ)�x1cY������yΐt��&K (RU�3u�o�vZ�gvݶ��N��!gW�z���)e,s�ٹ	�Y.���̇���|ocK��R(9	�/�z�-��Q���cз��"�Ԕ�e��KyiM�ӟ�w+����ܤx»/���ᦕ���O�R��Dd�51�A���qLL�����D��U��3^�'%��t���C��.bj���p�;��D���S��Wyڝl���?W��(�����������	y󨠍��2�R��eu�1�@��2��u:!(�#Yo}ԩ�SW�&���Y�����qr�MrYm�<�ݦ�,�4��\V:�tt1���9��y�#���Q��>�R�(d��3Bh��3���+Y:�\Z�y+UG�Z���n�t@�|���A
�b�]�E�z� TjV�,K��h(�/2��.PE�a�!]N��ބ�&�V�tWd/3�-��ӹ�߯۬[v�4������UE�4 ��&�$^@jG6���x�K�߿�6�ֽ���"\�BfM��o�\���Έ�i)$�)KA�SE�i�W��<ܾ
�5�P�"K���$�'d#
�<'1:��X�ڍ����p�o���FݥG����%W��ǃ��K�l<g`�(��W�zꋹ7_�Z���$�V��08O jf!IF�㨄!�"g�8��t���Q*Ro��5C?�*r]�j��u�����|坁72D70D��{d�,cQym��0�!�6�KbB����B�劊��g�u׉��:wf7��w�p�)g����Ƙ�:�0b�2�5��]�JEѝ-�����O�V�n�v]���ڣ`u7��*��y߉���Ӆ��P 9����a��8Ơ�Qb�5����}��zs��U�Yc��S=���0�������i%�EX9���L��nl�	U�l���*�����������KtT� �T�5ѵ�5`k)��3�ǋ�d��[�'��YF���|��߭���5g)�9j�)�,T�4�B
T���0�h��$���ax]��LN�B�,�5���p0�\)Q��$%����0@�/_��@
�0�C�(�
��J����h�#惤�"��jm���@⪥���0H�H7�-:����"�����k*���?�6����נYK����g!�4Rm���EE� �*��u5f��6�g:۾���܂`�翄�b�|���7S����:�
�R4N�ŨM�֙�,ȶ�-�mY���c''�����D��>���;ZL��e�8���?�wO�>>U�3����~E���	�*\`#�b�������\v{�v��y�0b���a�/����{w��������>NF���~D��������7Z���#�V'�$�����\͝�������7��..��k{ӆ�V�[7��x*�*,�@6-�xT�ؐ�w:�uO���r�Q;��~U0�,�W�ٱ�����(I��gHA�(e�]�D�z� �k&��u���n�ʨx�LEJ�Bg�C�E���tz��,n$��C�6��g�tCCMsi6�V���j�P�N7��u������w��LT)�%<u�Wǧ�դ����z�!$�W]�r8�\\�l}��� Z�A��{��2�)J�[X�ֹ>Js�}�t]{�^Sg��r�o}��Ҏ��o��\�uRjM�����B��G��!i>b�y��":$�2�7��W^6xS�׾���m����qL�8K�-DQ_ :\��y��H=�V`��"�a(N�2T:��5�oy��w�s<7ʻ-5~jf ��Bg���m���7�A��:( �x�E::�EE�R66.`�cf�=#{Wy@ip>���n�V�ssP�UR�n�IR�&2x�yA�4��Ȝ�V�����f�9�|����ѝ}a;�����/�[R�^��kf�5��-�%�����{&4c*��.C?�u� I��Xy�� �!�t�����"'���$�ZnJr���s_�;xZN��:$oڑ�"����F�p
���~�c�4x!D�Иc���1��\�EKd����a;K�7�Kh�^�D<GR�B�x�1��XHF�Z�h#�SGM�#r~o��k��/σ;����ۣ�<RE0ƥf� Pg�r��"V��忉	7����\��*�DD)�D@E�-r�YZ1b�*z�΅�,���4�¢c�!fڇ�65�&^�90#�RH;"��j)N[.!9$Xz'-���?��&n�ll�6t 3�d����}��=S�0[v��YX�%�[�P�9@
���`���i�#�b��1���`���L4w����~f��H���
����2�����0��c��kVI�֨�sf�?MKN�غ�&�YO�����ay��[o�ʒ�p�S��Q���ZP� �lɀC�i��,�{,��p� 0r ǰŲɦ�j��OǙ�f��C|kI\�a$�y7�N̈́7�	�����w�q&��s���ԲE�a�T�������Y�;'b�Ү״c��o���H�H�ޚ��"@i�6��A�%	�Lc ��!�h��D�g�_]Z��2�7��*7�.���Q����	6�d5�yz�[u��z�[k�����LU���n�v̂BԚH"��R&XH=Ɗ�:�|]�W-	a� P��	N�.p��m.$d��s%�1�%U�|��M-�1^q�F6�%2.��U��^��?������O�      6      x�3�L��".Cd�!����� �p�      �     x���K$+D�U{� *�'|do��Q�s�D32��^H}B� H)��{a�_�'�j|t,��N�QKPKS�3�w}�_���<|��I���m ��hR���Mk,?�;��*�v>򂳝+fzN�jM�R��AQ�;r��������{m@Mk_Ip�5(�⳩z�W^uW��~��B	ng@��QG=����~\�tH4��F0<��騌�3��s�`]�}!�	f� �Qi���8E,����׼���s�*{����}8 �n_�~9�G����n_�wF�'��"���.����Gƽ"�H���m_�Oϗk�۾��0Ohү��\а��!�5"Q^}eb�g�
�ocB���+n�{բ��Ȅ3��+g��;@�>��W��q��ݣ�]���`'��#��y�i��e��xm�c���z���K�n��\0�.N'C�j��g���T޾h� ��˶s�y^�6�Ϡ	sǯ��o �2
��G�_g<;����Ք���O:}�#��|�����      =      x������ � �      ~      x������ � �            x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      �      x������ � �      T      x������ � �      �      x������ � �      n      x������ � �      m      x������ � �      U      x������ � �      V      x������ � �      [      x������ � �      \      x������ � �      l      x������ � �      >   w  x��[�r7|��c_�E�
��~A�R� �*���le�����-���3�I�%�̥��:x�ڪ�e��j1r9��=�Y�EɎ�뉬GG��QWUJ���:�-\��?�w{s�?N����������t�>����+��/��0]��iPӐ)jh�F�4[�5i� y'�q��N	?���x9�LS��J�F]�v���,;ی;��y}������@i���zJ+�$j��x�bh��)��^J��K��A�%�%uuͻ����5W����y��۠m�����hX�z�*ԧT�\��T������������\�s�^O�B
8�Hʠ^yԂ��9����Yo??¾�|.�2d���d���.Q�"�qd�Ft���x�8:z�bBI�wר��a.�ZQs?]}��apr����f&:�8�$i)��J���P�_N`����>�����s����s'Ĭ��f<	T	���`�9����P�r����������ǹ���ɹ�Z��Hӣ�D�O�G��(=��9������\Z^�΅�{iу}pZX�+��O�ӽ�R];��^�N�{+�S@�xdp�4NyX�C'�zp՟9���䕯���be�l��L��R����G�O<�jx2Ym�E��@�k�4�f
�{����[\��_�;)��*�b��F�$Gb�����>X㚏M�+>z��NE.��%:���0!r.��D��������oOY�bp.�j�h���GgHAYH׭:}q{����~5<�d?���	F���
��^Kޛh�����\�su���B'��}�Fk�佱� ��ۛ/���?�N�"'}�h��
�����{��b�F��J[�"�'_(�䳹2a�`���G��8�}��V�F��o9`� ��;Xu��#�
=�wB�1n�,��z|.�R`���D8�8W�b���4����.?����I<��ܩ�i. �9q�l��F��H�H6� j�>]]�{��/u�$t�n��􊌁������:g˚��G&1��޽��=���*��"�4IHvX��*�z�B�����k(��~c0��DJ�Ҵ0QY&~��?�������`R�e����H>��P'�2S�������U{O����,�?���HIL��ѭ?�5$������O��.,���u���/
����m�7�i%�L�����)0�b�1T�"��5�ן �n>>J�WÓ%Zv6�*	!:����GЫg�πV�ٯ�1��ǈ~F�?�|��`��u������~R�Iƥی���?��r�~�J���W�����gf4�a�PuV��;�1�)9^�h�	ުa���Di�GLŘ�Ph5��U����O�w�j�50��Y$��
�:�l*�,E�L��"�M�n1��A��4
)�0p����M�[�A%�b3�.2xq�ks�$i�w�O#x+�8KKS+���r�����(}�X4�C�6����j����,��#�֌ˢ���|���ho����`�!:rUс��&�{��Í��93*3B}7��V�2P"z���B�Fm�z�ŉIaRuT�I�}udB'5��N�x���j�㊱�@5�J�u풚@���{�[���EI�嶬(΀�KT�fc����Í���M� HJ%3J���Q��k)P3<�AW#x+���xh�"K8��Tb�uB܂���G#x;��s��?)��0SmB�g�Z09��C#x�,�2���z�T\�
[������i��͢"�+*�3GK�p�!Y�	Ý���5���9�A���:W�w�t��S��!��io�:��_����i:�F�j
Ag��oo^w�~� �ll��=�I.�Q��P�^M��F�V�S.d���<B3PwͰ�ΏQ0�~�̮����T6X�h8�8*��Ẁ�kz�N�b��Km���^��aZQ{U�*0�4�N�C\sBԴ��7�֩�9���)�sW"l��'������_��Sa[�]x�����񅩕*+EKk3T���?��sC%ru^6�E�>�z�s��0�l���ֹ�}�H�E��Yl�c%oV�/��c����:�eM9 )a	/�!�L�'I\�ƚ���n�u�R)�G����N"^�����	}s`B�w[�N�5�,��s:���4���)Icț�����::42�0�?�q�϶��u�g�:��c��r[��}^�Ȓ�l��0W���Mh���l���:>$%l�z=�"�f��<co�A&$;�m�u*x��>��Z켶�A���㼈T���&�!�un@���_y�][Ќ�t&h|�N��v�lq[�u�d�qQ�z+.8�BN���g0���b�w[�W�]��bܗM����o"�����>�o��S�{'Qd4����:�s�J�m_P��m����Lf�4y �])K�u�R�
����7�ֹ�Υ��KXJ`��nY�d�V�F�yQtk����h�bb�ˉ��^���l���n�)R�>0��%"T�SZ
S5s\ێwA�ζ�S��ڨ�k"|��(	t?�A���Ķ�q��7�ݜ:Q�}G�����y��	k�g�܎���ݜ:��l����F�0Q���bPkA���$��vsn(!M�T�I�	�߻(�
i���7T����W�nN��y�ڤ�z�k�.�YR!���Ufe9>om�9�SMZ�P�����'p���BԒ����m7��K�L���5YMBG<B�1�w7g߿e�����A���U��]��Ow�+��%쬹�j՝ލ��N[�[�d&����fE
˅�#�@F���L���kJ��*���z�omx�{ӝ�X@&"����hnu8S��
n���X��y�Ξ��]jϨ3쫉�����i�ѓ)����i����ոX\�M��yBaqp���q���Ŏ�������n      Z   %   x�K4J�4�42�3�3�44�45176�0����� _H�      k      x������ � �      j      x������ � �      x      x������ � �      R   	  x��Y[n[G��\�6P�~?6������!�"�r��~N��ăq�k�2`}�ا���㲺�K�1�PꚉmF�Z[��*o��G;{�����Ŷˋ��J�r>۸m���k������Ů��a4癸�Hi�F��H���i�+c��ˁ�q��OS-���A����i��t|���o�v}����o��Q�n��0�1P�mtJ�vj3;m����wI�SlG��2�W�G�QG�4�v����t|�m��|z�A�w�~�׎�~�j+ڃ9r]2%Ε��J�6v��h*�Q�-و$;�,�h�A������t���{=�����c��[U�\&%e�yT#	�-1����n��f� \M��)T�3��q��|<��ϻ�}�/W<��~�Q�$����t`Ɋ祑�=�o<׶��/v�H�c��B�Ic!��ke-���������-��6F�V���7W��#�*э�|��H�8O�F���1<iN?'0͜ö������g�=�䎂�H8!��}x�\7|�<�8��P0��j#Ũ`�n���c�LP0����a���d�9�A�ԓ	�)[p���a����x�0�w_X���?s���=I�1+n̾}���3?j.�f-5�"���r�<U����Ve�������]Dmn�H15p�LI���j�������x���!��Vg"�W�!�Rp�%���%�)���A�:*EN��Z�J�s�����^V)�o�G3���1Qo1Sm���6k�왏�'�������D�q���@K�B����>�9�p�@Θ���$�� �w O_�z�G�B�bn!�*f�0�&���ޓ��]�X�������o�u~��J�)�n�z��fJ�'��PB�=8�E��TA�@�X�y�!A���+F����,wĴ2ك~R_��*8�A}q�&0I�f�L�r���Kt�����R�����Q��}�)ǭ$v�М�)3��ı]�O�O�?~�,�x_��i~S���OA.��҅=E��nY4��K�����#��=Zu�Qx�
�5Np�]H�.�Jj��դ,�*%��L"�}4��@�p�ō�HrĴԐ��Z4K"�=�N��I��c����(��t�?��0<_�џk��\�D��V���$;�����B�L\B%G,
9@_s��/�(`I���V$��M�V�1��25�(^�9�����,yW�$�QUG�p�������]=�C�[�F5!#��ҟ�NŃ�G����>�.�t�/�}JǊov��GG�sA�Ç.��9g�U�zHe�*��ۚe�3����ʹ:�㡖�����񁸀
*w8��}{�ђ����s�.�U"hI�H� T���cw�tȴMW�P�o,˪������`�sE-*v�5Q[@��J�)��vG�Tuh�Ol���B��e4�C���Z�Z��_�e�B5���1��X��cN'��Z��s:L14��1�χ��.n	��H��N 
^q&U���0~��1\���/(Ji��H�ٮ�R�\$>?w� Y���f�j���d_ѷ>����0��	eu�*�`ifC_0��@����&^��`�0#��k�G@[�<����C�6r�ށK@��RP�H�=��AO���F�U��[o!���S����+i�?wA(3�;������-��1���WP�#�s'e��)R���P��9�������] �ʵ���J�YQ���f��
�� p�ރ��|g#b�j��ZB���c�s_9Tgkpl�50��8�|6���|\�7~��UῨǴ��f�F���[��A��Y�S���p�;X]i$�t>?w!�qW�i��.s�n�d��2�d���3�6� n�:����
U~pD�����=C7���v�!���z�G�~9��p��A�:��@��F,CF@�1��F��`����C{j�i�s"a�2ю��,XSr&)��b�O��ּ'S�I�w��ϰ�#6��r�"dE�ɲ�9���@��{Ŭ��~B��%sFҫL ���kC8����^���=H*��<<��zo�Q��޿~1	���?{�z0�����?A;���$���I8z�P����Ĥ�D����dd���guC��y}���_D����\�M9�^rűi #��ղ`�����<R{$~���^c�PV�kQ�5�������p��U�X�aLW�Êr+d8F�
.L����X�%����
"��(����#d�{��t�l�	LK�R{tH�P�3!#G]��ӿ�� ��5�      S   �
  x���Ɏ#������+�/G��`>(0�yTRA�cl����e�NE���� =*}A��2�dV�jk�Z��b�!W������ʇ��x��i����_������x�|�2�_��Cy�v�l�����Z���;��x:����䧝>}�������^�ӑK?~t%�&_��l#�c'�*R���XmsU||��?v���������8_^���=�/���t���L�z&瓦첦⇵�UG�Y���Z��ny�pp�q�f1@�|1�����eeCE�b2exo�����!��r���7��'?���7��w���N�M�/+���ǐgc`Gc_��ϵȸ��88�_/}�˥��ߝ��b�w#����Y����+2��\ÐgoMC��^�/wr�Wr��D�<����Ǥ�)ݒu)��S�1'űES\V;C�Χ��k�q�+�qp"�c.ߨ�*��[�d�ɦcM�S��y����社�﯍����z'3l|-3�d~��g�~'4���2'�|��Pȹш��C��Ɇ�!�.ן��2��W2��D�}�Q"��9�}N�|��$ߨ��H�l�Қ�J{:#�8\ޮ?MNB��Zg�������������=�A�j��K�X{O��ZM�Ū�'4����Yā�^��3�r�Ve���̶�̈c/�M��J�B��"�`�^v�/�֎}��q�w�*
N�U���s��K1��[+��.�J�M�)�9I��ݑ�����88���o������Da�r���J��R	[�ű�0��e���LM�<ܐe�<��fJI52����, ��������8���x�����������X�z'�A��e��֒Q.W��RX�w/o���7��g:?����q�x����T�Iq�H6�:S�!����W1�_�p������݉;�>Q`pv��~?�����C���|�|�S��.���U%�2�QN�SL��sq/��Ϟ�)^+�3�qoF���f�1���eo��QѦSP��+&k�?|�#��-ǧ����S�����(_?��x=�]��\�m�=��	k{�@�����:'�����^�����6���LOV k׻�����`r	V+2��aVb���K�߹��j1E���!����S����ܗp��;>����^?���}{ч��d�H��B>�l9�T4��ɄƦ��ګ�A���[g��:���YV	{�U����qDEY��$=�Is���+�{Ce�פ���x=���w�0!j��oVak��;�t>�<�/�߾��C���Sѱs�xI� n��Z��j�aطo�4�BA�D(��D(�������oZ���x�����^8hȍ�����)��r�{#�OT%l�X���|�:��^�A08�� �_O�EA��2}���w���|��)���֪��MS���P��	[[I���%���.����-��U5��G��t�Pbi��#SІs�63������ٛ���RJ���V����=�R��j��{' b�w⧽��䛁�f�D9x��R��r�Ժw7�UP���yg��x|�=�p�E���x�nwCv����zI�\�N��"Q߾ƭ�e���̸�w7P��g݃�H�DyT�ť���u�������z���Y�>\�d�i�k������:�Uӱ��a |���_�)0��?yZX�ȍ:yV�F�JQ+&�쏭v9��]J~/I����I���J��X��ol��H���S���<&*�z	6G�F����A����m��?�e�|��!���%F�4)�}mg��Q(�z�����Vߪh��TF��� �Qрع���"�#I�t9�$����H�xf�A�F�#nz�3Nt~���!n���V)S�9�s Q���a��M�Hⴷ�!|�}	7~W�@�i5�yվ�����\�5,M��*$�M;�n�!��F�nq������8
N�����l���L�w��P���d�O;�A�l��m��O��^i���s�Fe�(2�rj�$w�b��9(
^�Z��F���@��Mn{]e��Y������!�z�ȾB:���(Si�єwA���@���
��Jm�����7
C؆��'T��l�Z��b
�fe�茣彦�lxč�E����<ox�Ѝ������DF�"A��2G�RL�I���7{�>����13�bf���h�6��ԙ[�JLK��)[%�gk\2mo!C�no
��z�����=-�A�F�O&$;�D�,��R+�e��<�0,�;����`�4�N(�໽��x
w{'pӳ����wB[�4{K���?M9*yy_������n�!|�w7�~�g���^Oz' x���=$��,g���Sva�y��x%�]�h!��ݸ��F����{��([�}�7�e�^Z��_��,OL󜣤�ʺ��+DM
���ٵ?� ���]�!�A��.e�x����Y�]�\��p5�6�@���h��W��j5��σ�j������Y�>������=�08���\��wb6nis𑽡�NB֑�Y��x!���Ҥw�&+��������,��N@�ƽ�n���Hq5˝������{��
k�0�^
����ܿ��rJ�J�R%�sZ��R�{E:�_S��/�������kJ���?�>~��!���      ?      x�u�1�\)�k�x��L)�*E6E�4lm������_��K�E".���綗���:g�Z�F�s{��ׂ��?���%�}y}���	�~�����}�˟����Ƿ޿��4��s�1�16�qXY0k8ƌݻ��1�����*���Q�����_�޴r}�>=u`l��ˁ�h�	��ʶS���N��t�M
�}�d,����D5�y��5PQe욺�D��A3��	;,�ݔd�!���8S�b��+����A�5��j
�҇��%zj�Q��Y%=�<�^ؑ�����R�VԲ|��8+��v�h���#L�ɵ�j�(��T0w�a�2u��0�������/�y���q�}x�I)���H�y͗pۏ����p��d(���q�P�K��O,�&)�}w�M=M�;iՄ�H�{ǅŇb��D�H�'�t*�Q�>���'�:Y#6P�K�ۭL��4�znN�1�7��-ې)	b��r���W�]��-�B��B�z��9��+�f�4 �g=�OX�WVƳ�����������G:��      @   P  x���Yo�6ǟ�Oa�%O��W�l �c�h���I[�-�٬)R%)�F�߽#A|e���M�9?�����d�<�%`��V��/�p�;-�t!��u����j+��a7���Fa a3��uīմ�<�P�3��$�N�V�FR�*����������O�(X�\����;���t۟�_�h#G����5��gA�{Ǖ��8X��}~�㿁���rM{����׻3��8\́�q%�	��R���:#��7L�2+��36YN��f� ���P\	��RgL(Ħpp����Ǥ� �1���A�u�|lѽk&da�7&ʥi�s�(��L(�ٷB����M9��'�i+n �受�?�S_�o��Q��u��%H��E^֍z��Y��l�֣� �Y
�b	�_`
��]"l.��+J�f�L��l����$��$�
�����!������힝؜��������$��B���+�0F粯Q=��6i�Wk:M�P�3֢�o��F�P�r���y�e+�iVeƼ~��Chr2~x6��ŵ4lT�����j;kA�Ym�$�-���vsm ~XH�m��j�Zm 1������@2�����׽��
�}�49O0������f�r~�z��[MB푧�M�,��{���y�j�3�W�a�@/�Eu^�Ǚ�~W�@�z�&�v���9l�X�E�ˎ`R�fwR0�O4�Үbs�"��N��(q��gc|PJ�-`͐U����Pn�_:`��D@�0��m�n͈�vs5�@�HpUxT�|�J��
���}���֨-�/PʒG>4��i��0����}a1�;�F��I$j��U��ަ8�����oP��N�.���"���hI(�h?���g�R +��)����T ���ww1~��}_��ȴ���t������q�xC�(��O���)rڏao)F`&��v�'���q�9m�ē�Z��f�GYiS�c�!,C����"�ǌ,���rf�#|�elb�>������T��f��Ƀ�~p�~KFbxߒѤ�?ޒ���M�d$4�lVv�]~k�gw�d������,}l�ٍ��}�D���A���Fs      p      x������ � �      Y      x������ � �      A   \   x�]�1�0Й��#�88�Kl�T���K����;����J��@1��͙G^n{��g��;{�Q�ѽ6�y*�m�"�zP��_9֔�rn�      �      x������ � �      B   `   x�m�1� @ѹ��#;1����J�~�t���?�Z������I3��%&�2
�E&�(Z�y0���Dr�楢��Xm(,�Ճ���{�T$      C     x��RKN�0]ۧ����v,KV,�8�D��������Q"�P���F4U%VH�f~zo��hr%g0V*�j6OEYN�y1�R�1�*8;�#�X�ܘ�;�Z6ײ��+�z����. ��/�������+g�u$z�����
�*����Oƕqt13k����x�84��f�\���\`��2h�!������U�	�>�g�6��6�+����������K��~��_�d���h{��}�;���= 8~�q��;�      W   ;   x�3LN�H5MN�ML���5II1׵L2J�514113ON50N4�,*�2$F]jW� �1      D     x����j�0F��Gh�cٲ�,�Qd���Mi���t�����s�[S�ʪ@6��ĐS�rC�)��xs]��x�m�ۨf��e_��&+��԰Ӛj�5$2�l��Q{@��&@��%+��)R�Nk=�.w��˶.�a��H� �3P�y�Hs�\�O�~���p\��i��aB2-8���n@�0��(�JY��/�}��qYM���hB|.a��7�w3� �}�$�����pi�%�d������`�ͅ���3P��M X���
U�����0_,6��      i      x������ � �      h   8  x��U�j[I�}�~-�o�ϐ8K �	iҲP0�M�����}���iJcoz�b0�.#���9�ֵ�����GNPbw�R�.֒,��������쏳���l����#/�C�h� �x�	���R�R��t7��m�����	�
�lo�r��S�-h����p{qv?����\^]φ�O�w2�ݬ�b)�k�H�qb��6G�b�;O��5�B�l�Oq8�������������v8_����~#��it�Y�>](:�ęǧG�Z
W�66"�1��y���������~^oڏ?�K"��.�d)��;r�)�R�I�EⰓ���Wm^׫��-֫�pN58=����dJ���m"J���� #�T{�i���̹��~�.�ӳ��h/�D��'G�V����$`��5��&4l��c����l��ue���D44��/4z('�D�%"'+\�>`�P�Jg�Tr38|���w�`#[m���)2����8YR�:�M̤-�Ϊ=`��Z����o§'r.e�=������?��2�f��D�TYTOV�����ׂ�{�W,�T&	�D\)�o-A.V��xStlOZ�+مԵA6�w��*�b���v�u]��¼�ye�I�T4� G��7��)���x��D�mV�:4&�TS�9*Ī�`L���h�".}+:��@6PT�:�JAG�x�L�O�:z�c4`���|t�]r�7e�N-�<Jģ��q�FbB�A]3�[(��5��H�bo�E�� �D\r��J��'��ӨB;Cv&���G��x#���PI�kIm���P�uܞ��>Y��F]��F~��d2�̶�6      �      x������ � �      z      x������ � �      y      x������ � �      t      x������ � �      �      x������ � �      w      x������ � �      u      x������ � �      v      x������ � �      �      x������ � �      �      x������ � �      E   �   x����� �%��B $���'��֟��5|w2�^\}�$�"h�g���R9nT`��3�jy�H5��/�d:�s����\�{5����N�5��?�*����NX�ra����>�>XaǟS���KP[32���o$��c���W���^U*I��߷���M�      {      x������ � �      G   Q   x��A� ��ޅ^&�ݥ�a����߲%�"*Kn�r�E������[��V��c6O�;@Q�� VEw�6�s�Z_�p      ]      x������ � �      �      x������ � �      H   �   x�u�;n1Dk�]h�Ï��9���� Alx�@r��pm�Հ3�F:g�L�J��
�g��ud�sX8����ѝ!+g@_�jY����:�X��x�>O8&�J0#�t��:�4Ki��qٯ�G��������Qs�)�*�8�c�YRYi)�Wݏ��}]/o�����v%O���:��ն�@�<�Z2b��U�B�5sa�O��i۶�&]      I      x������ � �      d      x������ � �      e      x������ � �      J      x������ � �      o      x������ � �      K      x������ � �      L     x���˕% D��ra�ȥ7 ��;��k���{MOӂ:+1υqv�����Y^�@�v����r�!��T�O�>2��%*s�U�ְ�7%W��"еP��H`&\+9�Ǜ�͝BN����J2�'Q�Ֆ����F�
a��vc+Z���@N�G%��'�"N]���c���_�����Z���'��+݉���&�+���̽�d��	h�YR.͛7��7�6ӆ�x ɆVC}Xb��ϛ�,:}�D�zT��ӅL�>�4�Dm
j�t��3���	�͏���iI���*���ԥ��<�r���,���4�r� #��i�^)�{�wb���5g8ƣ�l�<���V�%�[�SB�,��DQR=����C�d�)t�F&\9�����������.��9r�{��L��י�ڝ�l�a,i�X�b�Yڑ����Dץݻ��g���7��Q�u=�Β��*��MKש_h���L�СK�A(�^�/y�=�;cnϜ��L����e��^��ڴ�7�����?�:��      M      x������ � �      X      x������ � �      F      x������ � �      N   b   x�̱� ��i��<`$,��ĩ�L���:��c8�>K���!]���_4�ե$2���LL[�����͓\Pׄ4���@O�u����ޫ���9�     